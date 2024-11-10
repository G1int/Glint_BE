package com.swyp.glint.chatting.repository;

import com.swyp.glint.chatting.domain.Chat;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/chatting/chatting-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class ChattingRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;


    @Test
    @DisplayName("채팅 저장")
    public void saveChat() {
        // given
        Chat chat = Chat.create("안녕하세요", 1L, 1L, LocalDateTime.of(2024,1,1,1,0,0));

        // when
        chatRepository.save(chat);

        // then
        Optional<Chat> savedChat = chatRepository.findById(chat.getId());
        assertThat(savedChat).isPresent();
        assertThat(savedChat.get().getMessage()).isEqualTo(chat.getMessage());
        assertThat(savedChat.get().getSendUserId()).isEqualTo(chat.getSendUserId());
        assertThat(savedChat.get().getChatRoomId()).isEqualTo(chat.getChatRoomId());
        assertThat(savedChat.get().getSendDate()).isEqualTo(chat.getSendDate());
    }

    @Test
    @DisplayName("채팅 조회")
    public void findChatMessage() {

        // given
        Long roomId = 1L;
        int page = 0;
        int size = 10;

        // when
        var chatMessages = chatRepository.findAllByChatRoomIdOrderBySendDateAtDesc(roomId, PageRequest.of(page, size));

        // then
        assertThat(chatMessages).isNotEmpty();
        assertThat(chatMessages.size()).isEqualTo(1);

    }


    @Test
    @DisplayName("채팅방의 마지막 메시지의 이전 메시지 조회")
    public void findAfterLastChatMessage() {

        // given
        Long roomId = 1L;
        int page = 0;
        int size = 10;

        // when
        var chatMessages = chatRepository.findAllByChatRoomIdOrderByIdDesc(roomId, 2L, PageRequest.of(page, size));

        // then
        assertThat(chatMessages).isNotEmpty();
        assertThat(chatMessages.size()).isEqualTo(1);

    }


    @Test
    @DisplayName("채팅방의 제일 최근 메시지 조회")
    public void findTop1ByChatRoomIdOrderByIdDesc() {

        // given
        Long roomId = 1L;

        // when
        var chatOptional = chatRepository.findTop1ByChatRoomIdOrderByIdDesc(roomId);

        // then
        assertThat(chatOptional).isPresent();
        Chat chat = chatOptional.get();
        assertThat(chat.getId()).isEqualTo(2L);
        assertThat(chat.getMessage()).isEqualTo("안녕하세요2");
        assertThat(chat.getSendUserId()).isEqualTo(1L);
        assertThat(chat.getChatRoomId()).isEqualTo(1L);
        assertThat(chat.getSendDate()).isEqualTo(LocalDateTime.of(2024,1,1,1,0,0));

    }




}
