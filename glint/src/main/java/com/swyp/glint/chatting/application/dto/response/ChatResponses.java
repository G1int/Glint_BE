package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.user.domain.UserDetailAggregation;
import lombok.Builder;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
public record ChatResponses(
        List<ChatResponse> chats
) {

    public static ChatResponses of(List<ChatResponse> chatResponseList) {
        return ChatResponses.builder()
                .chats(chatResponseList)
                .build();
    }
}
