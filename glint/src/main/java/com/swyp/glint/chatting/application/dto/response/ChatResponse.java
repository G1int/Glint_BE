package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.user.domain.UserDetailAggregation;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public record ChatResponse(
        Long id,
        String message,
        Long chatRoomId,
        Long userId,
        String nickname,
        String userProfileImageUrl,
        LocalDateTime sendDate
) {

    public ChatResponse(Long id, String message, Long chatRoomId, Long userId, String nickname, String userProfileImageUrl, LocalDateTime sendDate) {
        this.id = id;
        this.message = message;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.nickname = nickname;
        this.userProfileImageUrl = userProfileImageUrl;
        this.sendDate = sendDate;
    }

    public static ChatResponse of(Chat chat, UserDetailAggregation userDetailAggregation) {
        return ChatResponse.builder()
                .id(chat.getId())
                .message(chat.getMessage())
                .chatRoomId(chat.getChatRoomId())
                .userId(userDetailAggregation.getUserId())
                .nickname(userDetailAggregation.getNickname())
                .sendDate(chat.getSendDate())
                .build();
    }
}
