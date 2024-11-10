package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import org.springframework.transaction.annotation.Transactional;

public interface GetMeetingUseCase {
    MeetingDetailResponse getMeeting(Long meetingId);

    MeetingInfoResponses getNewMeeting(Long lastId, Integer size);

    MeetingInfoResponses getMyMeeting(Long userId, String meetingStatus, Long lastMeetingId, Integer limit);

    @Transactional(readOnly = true)
    MeetingInfoCountResponses searchMeeting(MeetingSearchCondition searchCondition, Long userId);
}
