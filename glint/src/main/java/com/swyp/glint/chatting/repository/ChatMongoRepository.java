package com.swyp.glint.chatting.repository;

import com.swyp.glint.chatting.application.dto.response.ChatResponse;
import com.swyp.glint.chatting.domain.Chat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//
//@Repository
//public interface ChatMongoRepository extends MongoRepository<Chat, String> {
//
////    @Query("""
////            select new com.swyp.glint.chatting.application.dto.response.ChatResponse(
////                c.id, c.message, c.chatRoomId, u.id, ud.nickname, ud.profileImage, c.sendDate
////            )
////            FROM Chat c join User u on c.sendUserId = u.id
////            join UserDetail ud on u.id = ud.userId
////            WHERE c.chatRoomId = :roomId
////            ORDER BY c.sendDate DESC
////    """)
////    List<ChatResponse> findAllByChatRoomIdOrderBySendDateAtDesc(Long roomId, PageRequest pageRequest);
//
//
//    @Query("""
//           {'chatRoomId': ?0}
//    """)
//    List<ChatResponse> tfindAllByChatRoomIdOrderBySendDateAtDesc(Long roomId, PageRequest pageRequest);
//}
