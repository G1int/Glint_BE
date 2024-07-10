package com.swyp.glint.chatting.application.dto.response;

import com.swyp.glint.chatting.domain.ChatRoom;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

public record ChatRoomResponse(
        @Parameter(description = "채팅방 ID", example = "1", required = true)
        Long chatRoomId,
        @Parameter(description = "Meeting ID", example = "1", required = true)
        Long meetingId,
        @Parameter(description = "채팅방 참여 userId List", example = "[1,2,3]", required = true)
        List<Long> userIds,
        @Parameter(description = "채팅방 이름", example = "채팅방", required = true)
        String name,
        @Parameter(description = "채팅방 활성화 여부", example = "true", required = true)
        Boolean isActivated,
        @Parameter(description = "채팅방 아카이브 여부", example = "false", required = true)
        Boolean isArchived
) {


    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return new ChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getMeetingId(),
                chatRoom.getUserIds(),
                chatRoom.getName(),
                chatRoom.isActivated(),
                chatRoom.getIsArchived()
        );
    }


}
