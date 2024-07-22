package com.swyp.glint.chatting.application;

import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.chatting.repository.ChatRoomRepository;
import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.common.exception.NotFoundEntityException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;


    public ChatRoomResponse createChatRoom(Long meetingId, ChatRoomRequest chatRoomRequest) {
        ChatRoom chatRoom = chatRoomRequest.toEntity(meetingId);

        //todo 요청 유저가 미팅에 모두 속해있는지 검증 로직 추가
//        Meeting meeting = meetingService.getMeeting(chatRoom.getMeetingId());
//
//        List<Long> invalidUserIds = meeting.isJoinUsers(chatRoom.getUserIds());
//        if(!invalidUserIds.isEmpty()) {
//            throw new InvalidValueException("Invalid User Contain" + invalidUserIds);
//        }

        return ChatRoomResponse.from(chatRoomRepository.save(chatRoom));
    }

    @Transactional
    public void removeChatRoom(Long meetingId) {
        ChatRoom chatRoom = chatRoomRepository.findByMeetingId(meetingId)
                .orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom MeetingId" + meetingId));

        chatRoom.archive();

        chatRoomRepository.save(chatRoom);
    }

    public void activeChatRoom(Long meetingId) {
        ChatRoom chatRoom = chatRoomRepository.findByMeetingId(meetingId)
                .orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom MeetingId" + meetingId));

        chatRoom.active();

        chatRoomRepository.save(chatRoom);
    }


    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom Id : " + chatRoomId));
    }

    public ChatRoomResponse getChatRoomByMeetingId(Long meetingId) {
        return ChatRoomResponse.from(chatRoomRepository.findByMeetingId(meetingId).orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom meetingId : " + meetingId)));
    }
}
