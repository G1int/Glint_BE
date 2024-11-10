package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import org.springframework.transaction.annotation.Transactional;

public interface UserMeetingUseCase {
    @Transactional
    MeetingDetailResponse userMeetingOut(Long meetingId, Long userId);

    void deleteUserMeeting(Long userId);
}
