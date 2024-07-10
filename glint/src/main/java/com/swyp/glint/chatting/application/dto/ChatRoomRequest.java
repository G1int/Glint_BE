package com.swyp.glint.chatting.application.dto;

import com.swyp.glint.chatting.domain.ChatRoom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

public record ChatRoomRequest(
        @Parameter(description = "채팅방 참여 userId List", example = "[1,2,3]", required = true)
        List<Long> userIds
) {


    public ChatRoom toEntity(Long meetingId) {
        return ChatRoom.createByMeeting(meetingId, userIds());
    }

}
