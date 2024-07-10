package com.swyp.glint.chatting.api;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping()
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성", description = "User Id를 통한 User 정보 조회")
    @PostMapping(path = "/meetings/{meetingId}/chatrooms", consumes = "application/json", produces = "application/json")
    public ChatRoomResponse createChatRoom(@PathVariable Long meetingId, @RequestBody ChatRoomRequest chatRoomRequest) {
        return chatRoomService.createChatRoom(meetingId, chatRoomRequest);
    }


    @Operation(summary = "채팅방 조회", description = "Metting Id를 통한 채팅방 정보 조회")
    @GetMapping(path = "/meetings/{meetingId}/chatrooms", consumes = "application/json", produces = "application/json")
    public ChatRoomResponse geteChatRoom(@PathVariable Long meetingId) {
        return chatRoomService.getChatRoomByMeetingId(meetingId);
    }


}
