package com.swyp.glint.user.repository;

import com.swyp.glint.user.application.dto.UserMeetingResponse;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
        """
            SELECT u
            FROM User u
            WHERE u.email = :email
        """)
    Optional<User> findByEmail(String email);


    @Query(
        """
            SELECT new com.swyp.glint.user.domain.UserMeeting(
                u.id,
                ud.profileImage,
                ud.nickname,
                ud.gender
            )
            FROM User u 
            left join UserDetail ud on u.id = ud.userId
            left join UserProfile up on u.id = up.userId
            WHERE u.id in :userIds
        """)
    List<UserMeeting> findByUserIds(List<Long> userIds);
}
