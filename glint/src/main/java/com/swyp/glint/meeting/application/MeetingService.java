package com.swyp.glint.meeting.application;


import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.repository.MeetingRepository;
import com.swyp.glint.user.application.UserFacade;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final UserService service;
    private final UserService userService;


    public Meeting getMeeting(Long meetingId) {
        return null;
    }

    public Meeting getUserMeeting(Long userId) {
        meetingRepository.findByUserId(userId);
        return null;
    }


    @Transactional
    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();
        meetingRepository.save(meeting);
        List<UserMeetingResponse> userMeetingResponseList = userService.getUserMeetingResponseList(meeting.getJoinUserIds());
        return MeetingResponse.from(meeting, userMeetingResponseList);
    }

}
