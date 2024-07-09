package com.swyp.glint.user.repository;

import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

    @Query(
            """
                SELECT ud
                FROM UserDetail ud
                WHERE ud.nickname = :nickname
            """
    )
    Optional<UserDetail> findByNickname(String nickname);

    @Query(
            """
                SELECT ud
                FROM UserDetail ud
                WHERE ud.userId = :userId
            """
    )
    Optional<UserDetail> findByUserId(Long userId);
}