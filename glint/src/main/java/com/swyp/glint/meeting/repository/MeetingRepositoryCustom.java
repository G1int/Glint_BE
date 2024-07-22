package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.domain.MeetingInfo;

import java.util.List;

public interface MeetingRepositoryCustom {


    List<MeetingInfo> findAllMeetingInfoByStatus(Long userId, String status);

    List<MeetingInfo> findAllNotFinishMeeting(Long lastId, Integer size);

    List<MeetingInfo> searchMeeting(MeetingSearchCondition searchCondition);
}
