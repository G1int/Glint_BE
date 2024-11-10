package com.swyp.glint.user.repository;

import com.swyp.glint.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, UserProfileCustom {

    @Query(
            """
                SELECT up
                FROM UserProfile up 
                left join fetch up.work
                left join fetch up.university
                left join fetch up.university.universityCategory
                left join fetch up.location
                left join fetch up.religion
                left join fetch up.smoking
                left join fetch up.drinking
                left join fetch up.hashtags
                WHERE up.userId = :userId
            """
    )
    Optional<UserProfile> findByUserId(@Param("userId") Long userId);

    @Query(
            """
                SELECT up
                FROM UserProfile up
                WHERE up.userId IN :userIds
            """
    )
    List<UserProfile> findAllByUserId(List<Long> userIds);
}
