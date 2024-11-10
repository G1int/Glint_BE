package com.swyp.glint.chatting.application.usecase.impl;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.application.ChatService;
import com.swyp.glint.chatting.application.dto.request.CreateChatMessageRequest;
import com.swyp.glint.chatting.application.usecase.CreateChatMessageUseCase;
import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.meeting.application.service.MeetingService;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateChatMessageUseCaseImpl implements CreateChatMessageUseCase {

    private final ChatService chatService;

    private final ChatRoomService chatRoomService;

    private final MeetingService meetingService;

    private final UserDetailService userDetailService;

    @Transactional
    @Override
    public UserChat createChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        Chat chat = createChatMessageRequest.toEntity();

        ChatRoom chatRoom = chatRoomService.getChatRoom(chat.getChatRoomId());

        Meeting meeting = meetingService.getMeeting(chatRoom.getMeetingId());

        if(meeting.isNotJoinUser(chat.getSendUserId())) {
            log.error("Not Join User, SendUserId : {} MeetingId : {}", chat.getSendUserId(), chatRoom.getMeetingId());
            throw new InvalidValueException("Not Join User, SendUserId : " + chat.getSendUserId() + " MeetingId : " + chatRoom.getMeetingId());
        }

        if(chatRoom.isDeactivated()) {
            log.error("Not Active ChatRoom, ChatRoomId : {}", chatRoom.getId());
            throw new InvalidValueException("Not Active ChatRoom, ChatRoomId : " + chatRoom.getId());
        }

        UserDetail userDetail = userDetailService.getUserDetailBy(chat.getSendUserId());
        chatService.save(chat);

        return UserChat.of(chat, userDetail);
    }



}
