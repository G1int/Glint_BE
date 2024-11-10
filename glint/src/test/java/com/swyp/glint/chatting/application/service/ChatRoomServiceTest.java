package com.swyp.glint.chatting.application.service;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.chatting.repository.ChatRoomRepository;
import com.swyp.glint.core.common.exception.NotFoundEntityException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/chatting/chatting-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class ChatRoomServiceTest {


    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @Test
    @DisplayName("미팅방을 특정 미팅방 아이디로 조회 할 수 있다.")
    public void findByMeetingId() {

        // given
        Long meetingRoomId = 1L;

        // when
        Optional<ChatRoom> chatRoomOptional = chatRoomService.findBy(meetingRoomId);


        // then
        assertThat(chatRoomOptional).isPresent();
        ChatRoom chatRoom = chatRoomOptional.get();

        assertThat(chatRoom.getId()).isEqualTo(1L);
        assertThat(chatRoom.getMeetingId()).isEqualTo(1L);
        assertThat(chatRoom.getName()).isEqualTo("chatRoom1");
        assertThat(chatRoom.getUserIds()).containsExactly(1L, 2L, 3L, 4L);
    }

    @Test
    @DisplayName("미팅방을 특정 아이디로 조회 할 수 있다.")
    public void getChatRoomByMeetingId() {

        // given
        Long chatRoomId = 1L;

        // when
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);


        // then

        assertThat(chatRoom.getId()).isEqualTo(1L);
        assertThat(chatRoom.getMeetingId()).isEqualTo(1L);
        assertThat(chatRoom.getName()).isEqualTo("chatRoom1");
        assertThat(chatRoom.getUserIds()).containsExactly(1L, 2L, 3L, 4L);
    }

    @Test
    @DisplayName("미팅방을 특정 아이디로 조회 할 때 존재하지 않을경우 예외를 발생한다.")
    public void getChatRoomByChatRoomIdFail() {

        // given
        Long chatRoomId = 5L;

        // when
        // then
        assertThatThrownBy(() -> chatRoomService.getChatRoom(chatRoomId))
                .isInstanceOf(NotFoundEntityException.class)
                .hasMessage("Not Found ChatRoom Id : " + chatRoomId);
    }


    @Test
    @DisplayName("미팅방을 미팅 아이디로 조회 할 때 존재하지 않을경우 예외를 발생한다.")
    public void getChatRoomByMeetingIdFail() {

        // given
        Long meetingRoomId = 5L;

        // when
        // then
        assertThatThrownBy(() -> chatRoomService.getChatRoom(meetingRoomId))
                .isInstanceOf(NotFoundEntityException.class)
                .hasMessage("Not Found ChatRoom Id : " + meetingRoomId);
    }



    @Test
    @DisplayName("채팅방을 생성 할 수 있다.")
    public void saveChatRoom() {

        // given
        ChatRoom chatRoom = ChatRoom.createByMeeting(2L, List.of(1L, 2L, 3L, 4L));

        // when
        ChatRoom savedChatRoom = chatRoomService.save(chatRoom);

        // then
        assertThat(savedChatRoom.getId()).isNotNull();
        assertThat(savedChatRoom.getMeetingId()).isEqualTo(2L);
        assertThat(savedChatRoom.getName()).isNull();
        assertThat(savedChatRoom.getUserIds()).containsExactly(1L, 2L, 3L, 4L);
    }




}
