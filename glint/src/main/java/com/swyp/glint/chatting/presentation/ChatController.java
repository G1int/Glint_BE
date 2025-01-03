package com.swyp.glint.chatting.presentation;

import com.swyp.glint.chatting.application.dto.request.CreateChatMessageRequest;
import com.swyp.glint.chatting.application.usecase.GetChatUseCase;
import com.swyp.glint.chatting.application.usecase.CreateChatMessageUseCase;
import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.chatting.application.dto.response.ChatResponses;
import com.swyp.glint.chatting.exception.NotFoundChatRoomException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final GetChatUseCase getChatUseCase;

    private final CreateChatMessageUseCase createChatMessageUseCase;

    private final MessageSender messageSender;

    @MessageMapping("/chatrooms/{meetingId}")
    public void chatting(@DestinationVariable Long meetingId, @RequestBody CreateChatMessageRequest request) {
        // todo room 정보 없을때 처리
        if(meetingId == null) {
            throw new NotFoundChatRoomException("roomId is null");
        }
        UserChat chatMessage = createChatMessageUseCase.createChatMessage(request);
        messageSender.send("/sub/chatrooms/", meetingId , chatMessage);
    }

    @GetMapping(path = "/chatrooms/{roomId}/chats", produces = "application/json")
    @Operation(summary = "채팅방 메시지 조회, paging", description = "채팅방 페이징방식 메시지 조회")
    public ChatResponses getChatMessage(
            @PathVariable Long roomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
            ) {
        return getChatUseCase.getChatMessage(roomId, page, size);
    }

    @Operation(summary = "채팅방 메시지 조회, noOffSet", description = "채팅방 noOffSet방식 메시지 조회, lastChatId가 null이면 최신 메시지부터 조회")
    @GetMapping(path = "/chatrooms/{roomId}/chats/no-offset", produces = "application/json")
    public ChatResponses getChatMessageNoOffset(
            @RequestParam(required = false) Long lastChatId,
            @PathVariable Long roomId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        return getChatUseCase.getChatMessagesNoOffset(lastChatId, roomId, size);
    }

}
