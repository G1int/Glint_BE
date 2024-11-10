package com.swyp.glint.chatting.presentation;

import com.swyp.glint.chatting.domain.UserChat;

public interface MessageSender {
    void send(String destination, Long meetingId, UserChat userChat);
}
