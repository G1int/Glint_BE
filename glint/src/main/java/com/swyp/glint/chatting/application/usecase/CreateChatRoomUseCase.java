package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;

public interface CreateChatRoomUseCase {
    ChatRoomResponse createChatRoom(Long meetingId, ChatRoomRequest chatRoomRequest);
}
