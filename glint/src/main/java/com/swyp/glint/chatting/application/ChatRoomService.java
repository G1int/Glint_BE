package com.swyp.glint.chatting.application;

import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.chatting.repository.ChatRoomRepository;
import com.swyp.glint.core.common.exception.NotFoundEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;


    public Optional<ChatRoom> findBy(Long meetingId) {
        return chatRoomRepository.findByMeetingId(meetingId);
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        return findBy(chatRoomId).orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom Id : " + chatRoomId));
    }

    public ChatRoom getChatRoomByMeetingId(Long meetingId) {
        return findBy(meetingId).orElseThrow(() -> new NotFoundEntityException("Not Found ChatRoom meetingId : " + meetingId));
    }

    public ChatRoom save(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

}
