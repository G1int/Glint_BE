package com.swyp.glint.meeting.application;

import com.swyp.glint.meeting.domain.Join;
import com.swyp.glint.meeting.repository.JoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final JoinRepository joinRepository;


    private void joinMeetingRequest(Long userId, Long meetingId) {
        joinRepository.save(Join.createByRequest(userId, meetingId));
    }

    private void joinMeeting(Long userId, Long meetingId) {

    }


}
