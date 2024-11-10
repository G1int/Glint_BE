package com.swyp.glint.chatting.application.usecase;

import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/chatting/create-chat-room-usecase-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class CreateChatRoomUseCaseTest {

    @Autowired
    private CreateChatRoomUseCase createChatRoomUseCase;

    @Test
    @DisplayName("미팅을 생성할 경우 채팅방이 생성된다.")
    public void getChatRoomByMeetingId() {
        // given
        Long meetingId = 2L;
        ChatRoomRequest chatRoomRequest = new ChatRoomRequest(List.of(1L));

        // when
        ChatRoomResponse chatRoomResponse = createChatRoomUseCase.createChatRoom(meetingId, chatRoomRequest);

        // then
        assertThat(chatRoomResponse).isNotNull();
        assertThat(chatRoomResponse.chatRoomId()).isNotNull();
        assertThat(chatRoomResponse.meetingId()).isEqualTo(2L);
        assertThat(chatRoomResponse.isActivated()).isFalse();
        assertThat(chatRoomResponse.isArchived()).isFalse();
        assertThat(chatRoomResponse.userIds()).containsExactly(1L);
    }


}
