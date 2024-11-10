package com.swyp.glint.chatting.application.usecase.impl;

import com.swyp.glint.chatting.application.ChatService;
import com.swyp.glint.chatting.application.dto.response.ChatResponses;
import com.swyp.glint.chatting.application.usecase.GetChatUseCase;
import com.swyp.glint.chatting.domain.Chat;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetChatUseCaseImpl implements GetChatUseCase {

    private final ChatService chatService;

    @Override
    public ChatResponses getChatMessage(Long roomId, int page, int size) {
        //TODO
        // noOffset 방식, Monogo로 전환하기
        return ChatResponses.from(chatService.getChatMessage(roomId, page, size));
    }

    @Override
    public ChatResponses getChatMessagesNoOffset(Long lastChatId, Long chatRoomId, Integer size) {
        Optional<Long> lastChatIdOptional = Optional.ofNullable(lastChatId);

        if (lastChatIdOptional.isEmpty()) {
            return getChatMessage(chatRoomId, 0, size);
        }

        return ChatResponses.from(chatService.getUserChatBy(lastChatId, chatRoomId, size));
    }

}
