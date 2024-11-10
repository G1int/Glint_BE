package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.response.ChatResponses;

public interface GetChatUseCase {
    ChatResponses getChatMessage(Long roomId, int page, int size);

    ChatResponses getChatMessagesNoOffset(Long lastChatId, Long chatRoomId, Integer size);
}
