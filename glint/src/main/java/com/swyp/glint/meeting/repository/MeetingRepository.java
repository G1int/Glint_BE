package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long>, MeetingRepositoryCustom {


    @Query("""
        SELECT m
        FROM Meeting m join m.joinUserIds jui
        WHERE jui in :userId
        AND m.status != 'END'
       """)
    List<Meeting> findAllNotEndMeetingByUserId(Long userId);



}
