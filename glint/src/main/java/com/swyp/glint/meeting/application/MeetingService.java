package com.swyp.glint.meeting.application;


import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.LocationService;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.domain.LocationList;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingInfo;
import com.swyp.glint.meeting.domain.MeetingStatus;
import com.swyp.glint.meeting.repository.MeetingRepository;
import com.swyp.glint.user.application.UserDetailService;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final UserService userService;

    private final ChatRoomService chatRoomService;

    private final LocationService locationService;
    private final UserDetailService userDetailService;


    @Transactional
    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();
        //todo MeetingResponse 리팩토링
        Meeting savedMeeting = meetingRepository.save(meeting);
        List<UserMeetingResponse> userMeetingResponseList = userService.getUserMeetingResponseList(meeting.getJoinUserIds());
        LocationList locationList = getMeetingLocationList(savedMeeting);

        return MeetingResponse.from(savedMeeting, userMeetingResponseList, locationList.getLocationNames());
    }


    public MeetingResponse getMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + id));
        List<UserMeetingResponse> userMeetingResponseList = userService.getUserMeetingResponseList(meeting.getJoinUserIds());
        LocationList meetingLocationList = getMeetingLocationList(meeting);

        return MeetingResponse.from(meeting, userMeetingResponseList, meetingLocationList.getLocationNames());
    }

    public Meeting getMeetingEntity(Long id) {
        return meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + id));
    }


    @Transactional
    public MeetingResponse joinUser(Long meetingId, Long userId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + meetingId));
        userService.getUserById(userId);
        meeting.addUser(userId);

        if(meeting.isFull()) {
            chatRoomService.activeChatRoom(meetingId);
        }

        List<UserMeetingResponse> userMeetingResponseList = userService.getUserMeetingResponseList(meeting.getJoinUserIds());
        LocationList meetingLocationList = getMeetingLocationList(meeting);

        return MeetingResponse.from(meeting, userMeetingResponseList, meetingLocationList.getLocationNames());
    }

    public MeetingInfoResponses getMyMeeting(Long userId, String meetingStatus) {
        return MeetingInfoResponses.from(meetingRepository.findAllMeetingInfoByStatus(userId, meetingStatus));
    }

    public LocationList getMeetingLocationList(Meeting meeting) {
        List<Location> locations = locationService.getLocationsByIds(meeting.getLocationIds());
        return new LocationList(locations);
    }

    public MeetingInfoResponses getNewMeeting() {
        return MeetingInfoResponses.from(meetingRepository.findAllMeetingInfoByStatus());
    }
}
