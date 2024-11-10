package com.swyp.glint.chatting.domain;

import com.swyp.glint.user.domain.UserDetail;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserChat(
        Long id,
        String message,
        Long chatRoomId,
        Long userId,
        String nickname,
        String userProfileImageUrl,
        LocalDateTime sendDate
) {

    public UserChat(Long id, String message, Long chatRoomId, Long userId, String nickname, String userProfileImageUrl, LocalDateTime sendDate) {
        this.id = id;
        this.message = message;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.nickname = nickname;
        this.userProfileImageUrl = userProfileImageUrl;
        this.sendDate = sendDate;
    }

    public static UserChat of(Chat chat, UserDetail userDetail) {
        return UserChat.builder()
                .id(chat.getId())
                .message(chat.getMessage())
                .chatRoomId(chat.getChatRoomId())
                .userId(userDetail.getUserId())
                .nickname(userDetail.getNickname())
                .sendDate(chat.getSendDate())
                .userProfileImageUrl(userDetail.getProfileImage())
                .build();
    }
}
