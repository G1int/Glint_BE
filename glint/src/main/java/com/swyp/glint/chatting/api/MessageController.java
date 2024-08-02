package com.swyp.glint.chatting.api;

import com.swyp.glint.chatting.application.MessageService;
import com.swyp.glint.chatting.application.dto.response.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {


    private final MessageService messageService;


    @RequestMapping(value = "/send/message", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody ChatDTO chatDto) {
        messageService.sendMessage(chatDto);
        return ResponseEntity.ok("Message sent to ActiveMQ!");
    }

}
