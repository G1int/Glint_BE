package com.swyp.glint.chatting.domain;

import com.swyp.glint.user.domain.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserChat {

    private Long chatId;

    private String message;

    private User sendUser;

    private Long chatRoomId;

    private LocalDateTime sendDate;

    @Builder(access = lombok.AccessLevel.PRIVATE)
    private UserChat(Long chatId, String message, User sendUser, Long chatRoomId, LocalDateTime sendDate) {
        this.chatId = chatId;
        this.message = message;
        this.sendUser = sendUser;
        this.chatRoomId = chatRoomId;
        this.sendDate = sendDate;
    }

    public static UserChat of(Long chatId, String message, User sendUser, Long chatRoomId, LocalDateTime sendDate) {
        return UserChat.builder()
                .chatId(chatId)
                .message(message)
                .sendUser(sendUser)
                .chatRoomId(chatRoomId)
                .sendDate(sendDate)
                .build();
    }


}
