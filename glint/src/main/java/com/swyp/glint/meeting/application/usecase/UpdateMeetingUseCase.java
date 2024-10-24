package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateMeetingUseCase {
    @Transactional
    MeetingDetailResponse updateMeeting(Long meetingId, MeetingRequest meetingRequest);
}
