package com.swyp.glint.user.repository;

import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserSimpleProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserCustom {

    @Query(
        """
            SELECT u
            FROM User u
            WHERE u.email = :email
            AND u.archived = false
        """)
    Optional<User> findByEmail(@Param("email") String email);



    @Query(
        """
            SELECT new com.swyp.glint.user.domain.UserSimpleProfile(ud, up)
            FROM User u 
            left join UserDetail ud on u.id = ud.userId
            left join UserProfile up on u.id = up.userId
            WHERE u.id in :userIds
        """)
    List<UserSimpleProfile> findByUserIds(List<Long> userIds);



}
