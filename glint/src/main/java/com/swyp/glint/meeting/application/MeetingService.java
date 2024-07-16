package com.swyp.glint.meeting.application;


import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingResponses;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.repository.MeetingRepository;
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

    private final UserService userService;


    public MeetingResponses getUserMeeting(Long userId) {
        meetingRepository.findByUserId(userId);
//        return new MeetingResponses();
        return null;
    }

    @Transactional
    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();
        Meeting savedMeeting = meetingRepository.save(meeting);
        List<UserMeetingResponse> userMeetingResponseList = userService.getUserMeetingResponseList(meeting.getJoinUserIds());
        return MeetingResponse.from(savedMeeting, userMeetingResponseList);
    }


    public MeetingResponse getMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + id));
        List<UserMeetingResponse> userMeetingResponseList = userService.getUserMeetingResponseList(meeting.getJoinUserIds());
        return MeetingResponse.from(meeting, userMeetingResponseList);
    }

}
