package com.swyp.glint.chatting.business;

import com.swyp.glint.chatting.domain.ChatRoom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ChatRoomTest {

    @Test
    @DisplayName("채팅방을 삭제 할 수 있다.")
    public void archiveChatRoom() {
        // Given
        ChatRoom chatRoom = ChatRoom.createByMeeting(1L, new ArrayList<>());
        // When
        chatRoom.archive();
        // Then
        assertThat(chatRoom.getIsArchived()).isTrue();
    }


    @Test
    @DisplayName("채팅방을 비활성화 할 수 있다.")
    public void activateChatRoom() {
        // Given
        ChatRoom chatRoom = ChatRoom.createByMeeting(1L, new ArrayList<>());
        // When
        chatRoom.activate();
        // Then
        assertThat(chatRoom.getIsActivated()).isTrue();
    }

    @Test
    @DisplayName("채팅방에 참여한 유저들을 업데이트 할 수 있다.")
    public void updateJoinUsers() {
        // Given
        ChatRoom chatRoom = ChatRoom.createByMeeting(1L, new ArrayList<>());
        // When
        chatRoom.updateJoinUsers(List.of(1L, 2L, 3L));
        // Then
        assertThat(chatRoom.getUserIds()).containsExactly(1L, 2L, 3L);
    }

}
