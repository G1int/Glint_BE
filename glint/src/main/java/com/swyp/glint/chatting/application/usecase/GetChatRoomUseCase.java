package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;

public interface GetChatRoomUseCase {
    ChatRoomResponse getChatRoomByMeetingId(Long meetingId);
}
