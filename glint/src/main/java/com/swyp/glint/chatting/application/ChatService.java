package com.swyp.glint.chatting.application;

import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.chatting.application.dto.response.ChatResponses;
import com.swyp.glint.chatting.domain.Chat;
import com.swyp.glint.chatting.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public List<UserChat> getChatMessage(Long roomId, int page, int size) {
        //TODO
        // noOffset 방식, Monogo로 전환하기
        return chatRepository.findAllByChatRoomIdOrderBySendDateAtDesc(roomId, PageRequest.of(page, size));
    }

    public List<UserChat> getUserChatBy(Long lastChatId, Long chatRoomId, Integer size) {
        return chatRepository.findAllByChatRoomIdOrderByIdDesc(chatRoomId, lastChatId, PageRequest.of(0, size));
    }

    public Optional<Chat> findLatestChatMessage(Long chatRoomId) {

        return chatRepository.findTop1ByChatRoomIdOrderByIdDesc(chatRoomId);
    }

    public void save(Chat chat) {
        chatRepository.save(chat);
    }

}
