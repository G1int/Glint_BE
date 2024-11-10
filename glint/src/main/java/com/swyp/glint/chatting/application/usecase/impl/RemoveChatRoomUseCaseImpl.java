package com.swyp.glint.chatting.application.usecase.impl;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.core.common.exception.NotFoundEntityException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveChatRoomUseCaseImpl {

    private final ChatRoomService chatRoomService;


    @Transactional
    public void removeChatRoom(Long meetingId) {
        ChatRoom chatRoom = chatRoomService.findBy(meetingId)
                .orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom MeetingId" + meetingId));

        chatRoom.archive();

        chatRoomService.save(chatRoom);
    }



}
