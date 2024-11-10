package com.swyp.glint.user.infra;

import com.swyp.glint.user.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    Optional<UserDetail> findByNickname(@Param("nickname") String nickname);

    @Query(
            """
                SELECT ud
                FROM UserDetail ud
                WHERE ud.userId = :userId
            """
    )
    Optional<UserDetail> findByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT ud
        FROM UserDetail ud
        WHERE ud.userId IN :userIds
        """)
    List<UserDetail> findAllByUserId(List<Long> userIds);
}