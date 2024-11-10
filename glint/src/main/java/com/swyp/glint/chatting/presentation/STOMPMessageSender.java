package com.swyp.glint.chatting.presentation;

import com.swyp.glint.chatting.domain.UserChat;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class STOMPMessageSender implements MessageSender {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void send(String destination, Long meetingId, UserChat userChat) {
        simpMessagingTemplate.convertAndSend("/sub/chatrooms/" + meetingId, userChat);
    }


}
