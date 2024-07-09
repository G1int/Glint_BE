package com.swyp.glint.user.repository;

import com.swyp.glint.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query(
            """
                SELECT up
                FROM UserProfile up
                WHERE up.userId = :userId
            """
    )
    Optional<UserProfile> findByUserId(Long userId);
}
