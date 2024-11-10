package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.response.ChatResponses;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/chatting/get-chat-usecase-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class GetChatUseCaseTest {

    @Autowired
    private GetChatUseCase getChatUseCase;

    @Test
    @DisplayName("채팅 메시지를 Paging 방식으로 조회할 수 있다.")
    public void getChatMessage() {
        // given
        Long roomId = 1L;
        int page = 0;
        int size = 10;

        // when
        ChatResponses chatMessages = getChatUseCase.getChatMessage(roomId, page, size);

        // then
        assertThat(chatMessages).isNotNull();
        assertThat(chatMessages.chats()).hasSize(2);
        assertThat(chatMessages.chats().get(0)).satisfies(
                userChat -> {
                    assertThat(userChat.id()).isEqualTo(2L);
                    assertThat(userChat.message()).isEqualTo("안녕하세요2");
                    assertThat(userChat.chatRoomId()).isEqualTo(1L);
                    assertThat(userChat.userId()).isEqualTo(1L);
                    assertThat(userChat.nickname()).isEqualTo("test");
                    assertThat(userChat.userProfileImageUrl()).isEqualTo("test.jpg");
                    assertThat(userChat.sendDate()).isEqualTo(LocalDateTime.of(2024,1,1,1,0,0));
                }
        );
        assertThat(chatMessages.chats().get(1)).satisfies(
                userChat -> {
                    assertThat(userChat.id()).isEqualTo(1L);
                    assertThat(userChat.message()).isEqualTo("안녕하세요");
                    assertThat(userChat.chatRoomId()).isEqualTo(1L);
                    assertThat(userChat.userId()).isEqualTo(1L);
                    assertThat(userChat.nickname()).isEqualTo("test");
                    assertThat(userChat.userProfileImageUrl()).isEqualTo("test.jpg");
                    assertThat(userChat.sendDate()).isEqualTo(LocalDateTime.of(2024,1,1,0,0,0));
                }
        );
    }


    @Test
    @DisplayName("채팅 메시지를 non-offset 방식으로 조회할 수 있다.")
    public void getChatMessageNonOffset() {
        // given
        Long roomId = 1L;
        int size = 10;
        Long lastChatId = 2L;

        // when
        ChatResponses chatMessages = getChatUseCase.getChatMessagesNoOffset(lastChatId, roomId, size);

        // then
        assertThat(chatMessages).isNotNull();
        assertThat(chatMessages.chats()).hasSize(1);
        assertThat(chatMessages.chats().get(0)).satisfies(
                userChat -> {
                    assertThat(userChat.id()).isEqualTo(1L);
                    assertThat(userChat.message()).isEqualTo("안녕하세요");
                    assertThat(userChat.chatRoomId()).isEqualTo(1L);
                    assertThat(userChat.userId()).isEqualTo(1L);
                    assertThat(userChat.nickname()).isEqualTo("test");
                    assertThat(userChat.userProfileImageUrl()).isEqualTo("test.jpg");
                    assertThat(userChat.sendDate()).isEqualTo(LocalDateTime.of(2024,1,1,0,0,0));
                }
        );
    }


}
