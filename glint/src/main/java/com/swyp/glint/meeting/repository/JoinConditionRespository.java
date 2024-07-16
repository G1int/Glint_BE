package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.MeetingJoinCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinConditionRespository extends JpaRepository<MeetingJoinCondition, Long> {
}
