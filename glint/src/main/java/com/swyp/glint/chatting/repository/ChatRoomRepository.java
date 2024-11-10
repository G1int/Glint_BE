package com.swyp.glint.chatting.repository;

import com.swyp.glint.chatting.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

    @Query("""
            select c 
            from ChatRoom c
            join fetch c.userIds uid
            where c.meetingId = :meetingId
        """)
    Optional<ChatRoom> findByMeetingId(Long meetingId);
}
