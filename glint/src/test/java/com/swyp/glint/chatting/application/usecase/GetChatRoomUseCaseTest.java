package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/chatting/get-chat-room-usecase-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class GetChatRoomUseCaseTest {

    @Autowired
    private GetChatRoomUseCase getChatRoomUseCase;

    @Test
    @DisplayName("미팅 아이디로 채팅방을 만들 수 있다.")
    public void getChatRoomByMeetingId() {
        // given
        Long meetingId = 1L;

        // when
        ChatRoomResponse chatRoomResponse = getChatRoomUseCase.getChatRoomByMeetingId(meetingId);

        // then
        assertThat(chatRoomResponse).isNotNull();
        assertThat(chatRoomResponse.chatRoomId()).isEqualTo(1L);
        assertThat(chatRoomResponse.meetingId()).isEqualTo(1L);
        assertThat(chatRoomResponse.isActivated()).isTrue();
        assertThat(chatRoomResponse.isArchived()).isFalse();
        assertThat(chatRoomResponse.name()).isEqualTo("chatRoom1");

    }


}
