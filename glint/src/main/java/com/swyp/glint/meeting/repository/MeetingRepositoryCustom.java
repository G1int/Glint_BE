package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.domain.MeetingInfo;

import java.util.List;

public interface MeetingRepositoryCustom {


    List<MeetingInfo> findAllMeetingInfoByStatus(Long userId, String status);

    List<MeetingInfo> findAllProgressMeeting();
}
