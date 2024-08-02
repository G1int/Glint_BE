package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.user.domain.UserDetailAggregation;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatDTO(
        Long id,
        String message,
        Long chatRoomId,
        Long userId,
        String nickname,
        String userProfileImageUrl,
        LocalDateTime sendDate
) {

    public ChatDTO(Long id, String message, Long chatRoomId, Long userId, String nickname, String userProfileImageUrl, LocalDateTime sendDate) {
        this.id = id;
        this.message = message;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.nickname = nickname;
        this.userProfileImageUrl = userProfileImageUrl;
        this.sendDate = sendDate;
    }

    public static ChatDTO of(Chat chat, UserDetailAggregation userDetailAggregation) {
        return ChatDTO.builder()
                .id(chat.getId())
                .message(chat.getMessage())
                .chatRoomId(chat.getChatRoomId())
                .userId(userDetailAggregation.getUserId())
                .nickname(userDetailAggregation.getNickname())
                .sendDate(chat.getSendDate())
                .userProfileImageUrl(userDetailAggregation.getProfileImage())
                .build();
    }
}
