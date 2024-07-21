package com.swyp.glint.meeting.application;


import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.keyword.application.LocationService;
import com.swyp.glint.keyword.application.ReligionService;
import com.swyp.glint.keyword.application.SmokingService;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.domain.LocationList;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingAggregation;
import com.swyp.glint.meeting.repository.MeetingRepository;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.domain.UserSimpleProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final UserService userService;

    private final ChatRoomService chatRoomService;

    private final LocationService locationService;

    private final DrinkingService drinkingService;

    private final SmokingService smokingService;

    private final ReligionService religionService;


    @Transactional
    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();

        Map<Long, Drinking> drinkingIdMap = drinkingService.getAllDrinking().stream().collect(Collectors.toMap(Drinking::getId, drinking -> drinking));
        Map<Long, Smoking> smokingIdMap = smokingService.getAllSmoking().stream().collect(Collectors.toMap(Smoking::getId, smoking -> smoking));
        Map<Long, Religion> longReligionIdMap = religionService.getAllReligion().stream().collect(Collectors.toMap(religion -> religion.getId(), religion -> religion));

        //todo MeetingResponse 리팩토링
        Meeting savedMeeting = meetingRepository.save(meeting);

        LocationList locationList = getMeetingLocationList(savedMeeting);
        List<UserSimpleProfile> userSimpleProfileList = userService.getUserSimpleProfileList(meeting.getJoinUserIds());
        MeetingAggregation meetingAggregation = new MeetingAggregation(
                meeting,
                userSimpleProfileList,
                locationList,
                drinkingIdMap,
                smokingIdMap,
                longReligionIdMap
        );

        return MeetingResponse.from(meetingAggregation);
    }


    public MeetingResponse getMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + id));

        MeetingAggregation meetingAggregation = getMeetingAggregation(meeting);


        return MeetingResponse.from(meetingAggregation);
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

        MeetingAggregation meetingAggregation = getMeetingAggregation(meeting);
        return MeetingResponse.from(meetingAggregation);
    }


    public MeetingInfoResponses getMyMeeting(Long userId, String meetingStatus) {
        return MeetingInfoResponses.from(meetingRepository.findAllMeetingInfoByStatus(userId, meetingStatus));
    }

    public LocationList getMeetingLocationList(Meeting meeting) {
        List<Location> locations = locationService.getLocationsByIds(meeting.getLocationIds());
        return new LocationList(locations);
    }

    public MeetingInfoResponses getNewMeeting(Long lastId, Integer size) {
        return MeetingInfoResponses.from(meetingRepository.findAllNotFinishMeeting(lastId, size));
    }


    private MeetingAggregation getMeetingAggregation(Meeting meeting) {
        Map<Long, Drinking> drinkingIdMap = drinkingService.getAllDrinking().stream().collect(Collectors.toMap(Drinking::getId, drinking -> drinking));
        Map<Long, Smoking> smokingIdMap = smokingService.getAllSmoking().stream().collect(Collectors.toMap(Smoking::getId, smoking -> smoking));
        Map<Long, Religion> longReligionIdMap = religionService.getAllReligion().stream().collect(Collectors.toMap(religion -> religion.getId(), religion -> religion));
        LocationList locationList = getMeetingLocationList(meeting);

        List<UserSimpleProfile> userSimpleProfileList = userService.getUserSimpleProfileList(meeting.getJoinUserIds());
        MeetingAggregation meetingAggregation = new MeetingAggregation(
                meeting,
                userSimpleProfileList,
                locationList,
                drinkingIdMap,
                smokingIdMap,
                longReligionIdMap
        );
        return meetingAggregation;
    }

}
