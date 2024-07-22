package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinMeetingRepository extends JpaRepository<JoinMeeting, Long>, QuerydslPredicateExecutor<Meeting> {


    @Query("""
        SELECT j
        FROM JoinMeeting j 
        WHERE j.userId = :userId
        AND j.meetingId = :meetingId
        AND j.status = :joinMeetingStatus
    """)
    Optional<JoinMeeting> findByUserAndMeeting(Long userId, Long meetingId, String joinMeetingStatus);

    @Query(
            """
            SELECT j
            FROM JoinMeeting j
            WHERE j.meetingId = :meetingId
            AND j.status = 'WAITING'
            AND j.id > :lastJoinMeetingId
            order by j.id limit 10
            """
    )
    List<JoinMeeting> findByMeetingId(Long meetingId, Long lastJoinMeetingId);


    @Query(
            """
            SELECT j
            FROM JoinMeeting j
            WHERE j.meetingId = :meetingId
            AND j.status = :status
            """
    )
    List<JoinMeeting> findByMeetingIdAndStatus(Long meetingId, String status);
}
