package com.swyp.glint.chatting.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Table(name = "chat")
@Entity
//mongoDB시 사용예정
//@Document(collection = "chat")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Long sendUserId;

    private Long chatRoomId;

    private LocalDateTime sendDate;


    @Builder(access = lombok.AccessLevel.PRIVATE)
    private Chat(Long id, String message, Long sendUserId, Long chatRoomId, LocalDateTime sendDate) {
        this.id = id;
        this.message = message;
        this.sendUserId = sendUserId;
        this.chatRoomId = chatRoomId;
        this.sendDate = sendDate;
    }

    public static Chat create(String message, Long sendUserId, Long chatRoomId, LocalDateTime sendDate) {
        return Chat.builder()
                .message(message)
                .sendUserId(sendUserId)
                .chatRoomId(chatRoomId)
                .sendDate(sendDate)
                .build();
    }



}
