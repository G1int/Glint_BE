package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.chatting.domain.UserChat;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record ChatResponses(
        List<UserChatResponse> chats
) {

    public static ChatResponses from(List<UserChat> userChatList) {
        return ChatResponses.builder()
                .chats(
                        userChatList.stream()
                                .map(UserChatResponse::from)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
