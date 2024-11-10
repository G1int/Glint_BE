package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.domain.LocationList;
import com.swyp.glint.meeting.domain.Meeting;
import org.springframework.transaction.annotation.Transactional;

public interface CreateMeetingUseCase {
    @Transactional
    MeetingDetailResponse createMeeting(MeetingRequest meetingRequest);
}
