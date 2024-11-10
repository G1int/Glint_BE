package com.swyp.glint.chatting.application.usecase.impl;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import com.swyp.glint.chatting.application.usecase.GetChatRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetChatRoomUseCaseImpl implements GetChatRoomUseCase {

    private final ChatRoomService chatRoomService;

    @Override
    public ChatRoomResponse getChatRoomByMeetingId(Long meetingId) {
        return ChatRoomResponse.from(chatRoomService.getChatRoomByMeetingId(meetingId));
    }



}
