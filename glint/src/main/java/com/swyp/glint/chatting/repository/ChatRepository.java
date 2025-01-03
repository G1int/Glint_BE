package com.swyp.glint.chatting.repository;

import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.chatting.domain.Chat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("""
            SELECT new com.swyp.glint.chatting.domain.UserChat(
                c.id, c.message, c.chatRoomId, u.id, ud.nickname, ud.profileImage, c.sendDate
            )
            FROM Chat c join User u on c.sendUserId = u.id
            join UserDetail ud on u.id = ud.userId
            WHERE c.chatRoomId = :roomId
            ORDER BY c.sendDate DESC
    """)
    List<UserChat> findAllByChatRoomIdOrderBySendDateAtDesc(Long roomId, PageRequest pageRequest);

    @Query("""
            SELECT new com.swyp.glint.chatting.domain.UserChat(
                c.id, c.message, c.chatRoomId, u.id, ud.nickname, ud.profileImage, c.sendDate
            )
            FROM Chat c join User u on c.sendUserId = u.id
            join UserDetail ud on u.id = ud.userId
            WHERE c.chatRoomId = :roomId
            AND c.id < :lastChatId
            ORDER BY c.id DESC
    """)
    List<UserChat> findAllByChatRoomIdOrderByIdDesc(Long roomId, Long lastChatId, PageRequest pageRequest);


    Optional<Chat> findTop1ByChatRoomIdOrderByIdDesc(Long chatRoomId);
}
