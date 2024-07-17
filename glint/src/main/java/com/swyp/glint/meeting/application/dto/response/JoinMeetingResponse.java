package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.JoinMeeting;

public record JoinMeetingResponse(
        Long id,
        Long userId,
        Long meetingId,
        String status) {

    public static JoinMeetingResponse from(JoinMeeting joinMeeting) {
        return new JoinMeetingResponse(
                joinMeeting.getId(),
                joinMeeting.getUserId(),
                joinMeeting.getMeetingId(),
                joinMeeting.getStatus()
        );
    }
}
