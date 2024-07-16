package com.swyp.glint.user.repository;

import com.swyp.glint.user.application.dto.UserMeetingResponse;
import com.swyp.glint.user.domain.User;
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
            SELECT new com.swyp.glint.user.application.dto.UserMeetingResponse(
                u.id,
                ud.profileImage,
                ud.nickname,
                ud.gender
            )
            FROM User u join UserDetail ud on u.id = ud.userId
            join UserProfile up on u.id = up.userId
            WHERE u.id in :userIds
        """)
    List<UserMeetingResponse> findByUserIds(List<Long> userIds);
}
