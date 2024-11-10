package com.swyp.glint.chatting.application.dto.request;

import com.swyp.glint.chatting.domain.Chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ChatDTO(
        Long userId,
        Long chatroomId,
        String message,
        String sendDate
) {

    public Chat toEntity() {
        return Chat.create(
                message,
                userId,
                chatroomId,
                LocalDateTime.parse(sendDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }


}
