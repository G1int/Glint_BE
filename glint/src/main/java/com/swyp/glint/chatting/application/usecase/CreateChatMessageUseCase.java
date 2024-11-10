package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.request.CreateChatMessageRequest;
import com.swyp.glint.chatting.domain.UserChat;
import jakarta.transaction.Transactional;

public interface CreateChatMessageUseCase {
    @Transactional
    UserChat createChatMessage(CreateChatMessageRequest createChatMessageRequest);
}
