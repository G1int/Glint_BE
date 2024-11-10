package com.swyp.glint.chatting.application.usecase.impl;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import com.swyp.glint.chatting.application.dto.response.ChatRoomResponse;
import com.swyp.glint.chatting.application.usecase.CreateChatRoomUseCase;
import com.swyp.glint.chatting.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateChatRoomUseCaseImpl implements CreateChatRoomUseCase {

    private final ChatRoomService chatRoomService;

    @Override
    public ChatRoomResponse createChatRoom(Long meetingId, ChatRoomRequest chatRoomRequest) {
        ChatRoom chatRoom = chatRoomRequest.toEntity(meetingId);

        //todo 요청 유저가 미팅에 모두 속해있는지 검증 로직 추가
//        Meeting meeting = meetingService.getMeeting(chatRoom.getMeetingId());
//
//        List<Long> invalidUserIds = meeting.isJoinUsers(chatRoom.getUserIds());
//        if(!invalidUserIds.isEmpty()) {
//            throw new InvalidValueException("Invalid User Contain" + invalidUserIds);
//        }

        return ChatRoomResponse.from(chatRoomService.save(chatRoom));
    }



}
