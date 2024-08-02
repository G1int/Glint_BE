package com.swyp.glint.chatting.application;

import com.swyp.glint.chatting.application.dto.response.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    @Value("${activemq.queue.name}")
    private String queueName;

    private final JmsTemplate jmsTemplate;

    public void sendMessage(ChatDTO chatDto) {
        log.info("message sent: {}", chatDto.toString());
        jmsTemplate.convertAndSend(queueName, chatDto);
    }

}
