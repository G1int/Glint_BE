package com.swyp.glint.chatting.presentation;

import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import com.swyp.glint.chatting.application.usecase.CreateChatRoomUseCase;
import com.swyp.glint.chatting.application.usecase.GetChatRoomUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final GetChatRoomUseCase getChatRoomUseCase;
    private final CreateChatRoomUseCase createChatRoomUseCase;


    @Operation(summary = "채팅방 생성", description = "미팅 채팅방 생성")
    @PostMapping(path = "/meetings/{meetingId}/chatrooms", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ChatRoomResponse> createChatRoom(@PathVariable Long meetingId, @RequestBody ChatRoomRequest chatRoomRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createChatRoomUseCase.createChatRoom(meetingId, chatRoomRequest));
    }


    @Operation(summary = "채팅방 조회", description = "Metting Id를 통한 채팅방 정보 조회")
    @GetMapping(path = "/meetings/{meetingId}/chatrooms", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ChatRoomResponse> getChatRoom(@PathVariable Long meetingId) {
        return ResponseEntity.ok(getChatRoomUseCase.getChatRoomByMeetingId(meetingId));
    }


}
