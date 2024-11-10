package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.user.domain.UserDetail;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public record UserChatResponse(
        Long id,
        String message,
        Long chatRoomId,
        Long userId,
        String nickname,
        String userProfileImageUrl,
        String sendDate
) {

    public UserChatResponse(Long id, String message, Long chatRoomId, Long userId, String nickname, String userProfileImageUrl, String sendDate) {
        this.id = id;
        this.message = message;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.nickname = nickname;
        this.userProfileImageUrl = userProfileImageUrl;
        this.sendDate = sendDate;
    }

    public static UserChatResponse of(Chat chat, UserDetail userDetail) {
        return UserChatResponse.builder()
                .id(chat.getId())
                .message(chat.getMessage())
                .chatRoomId(chat.getChatRoomId())
                .userId(userDetail.getUserId())
                .nickname(userDetail.getNickname())
                .sendDate(chat.getSendDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .userProfileImageUrl(userDetail.getProfileImage())
                .build();
    }

    public static UserChatResponse from(UserChat userChat) {
        return UserChatResponse.builder()
                .id(userChat.id())
                .message(userChat.message())
                .chatRoomId(userChat.chatRoomId())
                .userId(userChat.userId())
                .nickname(userChat.nickname())
                .sendDate(userChat.sendDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .userProfileImageUrl(userChat.userProfileImageUrl())
                .build();
    }
}
