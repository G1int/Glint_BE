package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {


    @Query("""
        SELECT m
        FROM Meeting m join m.joinUserIds jui
        WHERE jui in :userId
    """)
    List<Meeting> findByUserId(Long userId);


    @Query("""
        SELECT m
        FROM Meeting m join m.joinUserIds jui
        WHERE jui in :userId
       """)
    List<Meeting> findAllByUserId(Long userId);


    @Query("""
        SELECT m
        FROM Meeting m 
        WHERE m.status = :meetingStatus
    """)
    List<Meeting> findByStatus(String meetingStatus);
}
