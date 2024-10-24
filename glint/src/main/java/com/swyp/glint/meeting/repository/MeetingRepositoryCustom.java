package com.swyp.glint.meeting.repository;

import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.domain.MeetingDetail;
import com.swyp.glint.meeting.domain.MeetingInfo;

import java.util.List;
import java.util.Optional;

public interface MeetingRepositoryCustom {


    List<MeetingInfo> findAllMeetingInfoByStatus(Long userId, String status, Long lastMeetingId, Integer limit);

    Optional<MeetingDetail> findMeetingDetail(Long meetingId);

    List<MeetingInfo> findAllNotFinishMeeting(Long lastId, Integer size);

    List<MeetingInfo> searchMeeting(MeetingSearchCondition searchCondition);

    MeetingInfoCountResponses searchMeetingWithTotalCount(MeetingSearchCondition searchCondition);
}
