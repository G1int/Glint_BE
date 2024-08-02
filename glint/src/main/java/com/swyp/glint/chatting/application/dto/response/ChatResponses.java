package com.swyp.glint.chatting.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ChatResponses(
        List<ChatDTO> chats
) {

    public static ChatResponses of(List<ChatDTO> chatResponseList) {
        return ChatResponses.builder()
                .chats(chatResponseList)
                .build();
    }
}
