package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import org.springframework.transaction.annotation.Transactional;

public interface MeetingJoinUseCase {
    @Transactional
    JoinMeetingResponse joinMeetingRequest(Long userId, Long meetingId);

    @Transactional
    JoinMeetingResponse acceptJoinMeeting(Long userId, Long meetingId);

    @Transactional
    void joinUser(Long meetingId, Long userId);
}
