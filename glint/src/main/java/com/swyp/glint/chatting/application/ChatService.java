package com.swyp.glint.chatting.application;

import com.swyp.glint.chatting.application.dto.request.CreateChatMessageRequest;
import com.swyp.glint.chatting.application.dto.response.ChatResponse;
import com.swyp.glint.chatting.application.dto.response.ChatResponses;
import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.chatting.repository.ChatRepository;
import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.MeetingService;
import com.swyp.glint.user.application.UserFacade;
import com.swyp.glint.user.domain.UserDetailAggregation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final UserFacade userFacade;

    private final ChatRoomService chatRoomService;

    private final MeetingService meetingService;


    public ChatResponse createChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        Chat chat = createChatMessageRequest.toEntity();

        ChatRoom chatRoom = chatRoomService.getChatRoom(chat.getChatRoomId());

        // 미팅에 속한 유저인지 검증 로직 추후 추가 예정
//        Meeting meeting = meetingService.getMeeing(chatRoom.getMeetingId());
//
//        if(!meeting.isJoinUser(chat.getSendUserId())) {
//            log.error("Not Join User, SendUserId : {} MeetingId : {}", chat.getSendUserId(), chatRoom.getMeetingId());
//            throw new InvalidValueException("Not Join User, SendUserId : " + chat.getSendUserId() + " MeetingId : " + chatRoom.getMeetingId());
//        }

        if(!chatRoom.isActivated()) {
            log.error("Not Active ChatRoom, ChatRoomId : {}", chatRoom.getId());
            throw new InvalidValueException("Not Active ChatRoom, ChatRoomId : " + chatRoom.getId());
        }

        UserDetailAggregation userDetailAggregation = userFacade.getUserDetailAggregation(chat.getSendUserId());
        chatRepository.save(chat);

        return ChatResponse.of(chat, userDetailAggregation);
    }


    public ChatResponses getChatMessage(Long roomId, int page, int size) {
        //TODO
        // noOffset 방식, Monogo로 전환하기
        return ChatResponses.of(chatRepository.findAllByChatRoomIdOrderBySendDateAtDesc(roomId, PageRequest.of(page, size)));
    }

    public ChatResponses getChatMessageNoOffset(Long lastChatId, Long chatRoomId, Integer size) {
        lastChatId = Optional.ofNullable(lastChatId)
                .orElseGet(() -> {
                    return chatRepository.findTop1ByChatRoomIdOrderByIdDesc(chatRoomId)
                            .map(Chat::getId)
                            .orElse(0L);
                });


        return ChatResponses.of(chatRepository.findAllByChatRoomIdOrderByIdDesc(chatRoomId, lastChatId, PageRequest.of(0, size)));
    }
}
