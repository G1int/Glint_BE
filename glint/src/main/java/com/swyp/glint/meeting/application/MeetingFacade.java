package com.swyp.glint.meeting.application;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.common.exception.ErrorCode;
import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.keyword.application.LocationService;
import com.swyp.glint.keyword.application.ReligionService;
import com.swyp.glint.keyword.application.SmokingService;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.domain.*;
import com.swyp.glint.searchkeyword.application.SearchKeywordService;
import com.swyp.glint.user.application.UserDetailService;
import com.swyp.glint.user.application.UserProfileService;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetingFacade {

    private final MeetingService meetingService;
    private final ChatRoomService chatRoomService;
    private final JoinMeetingService joinMeetingService;

    private final UserProfileService userProfileService;
    private final UserDetailService userDetailService;
    private final UserService userService;
    private final DrinkingService drinkingService;
    private final SmokingService smokingService;
    private final ReligionService religionService;
    private final LocationService locationService;
    private final SearchKeywordService searchKeywordService;



    @Transactional
    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();

        Map<Long, Drinking> drinkingIdMap = drinkingService.getAllDrinking().stream().collect(Collectors.toMap(Drinking::getId, drinking -> drinking));
        Map<Long, Smoking> smokingIdMap = smokingService.getAllSmoking().stream().collect(Collectors.toMap(Smoking::getId, smoking -> smoking));
        Map<Long, Religion> longReligionIdMap = religionService.getAllReligion().stream().collect(Collectors.toMap(Religion::getId, religion -> religion));

        Meeting savedMeeting = meetingService.save(meeting);
        LocationList locationList = getMeetingLocationList(savedMeeting);
        List<UserSimpleProfile> userSimpleProfileList = userService.getUserSimpleProfileList(meeting.getJoinUserIds());
        joinMeetingService.createMeetingJoin(meetingRequest.leaderUserId(), savedMeeting.getId());

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
    public LocationList getMeetingLocationList(Meeting meeting) {
        List<Location> locations = locationService.getLocationsByIds(meeting.getLocationIds());
        return new LocationList(locations);
    }

    @Transactional
    public JoinMeetingResponse joinMeetingRequest(Long userId, Long meetingId) {
        Meeting meeting = meetingService.getMeetingEntity(meetingId);
        UserProfile userProfile = userProfileService.getUserProfileEntityById(userId);
        UserDetail userDetail = userDetailService.getUserDetail(userId);
        UserDetail leaderUserDetail = userDetailService.getUserDetail(meeting.getLeaderUserId());

        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(userProfile, userDetail ,leaderUserDetail, meeting);

        if(!userMeetingValidator.validate()) {
            throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
        }

        JoinMeeting joinMeeting = joinMeetingService.save(JoinMeeting.createByRequest(userId, meetingId));
        return JoinMeetingResponse.from(joinMeeting);
    }



    @Transactional
    public JoinMeetingResponse acceptJoinMeeting(Long userId, Long meetingId) {
        JoinMeeting joinMeeting = joinMeetingService.getUserWaitingJoinMeetingEntity(userId, meetingId);
        joinMeeting.accept();
        joinUser(meetingId, userId);
        joinMeetingService.save(joinMeeting);
        return JoinMeetingResponse.from(joinMeeting);
    }


    public MeetingAggregation getMeetingAggregation(Meeting meeting) {
        Map<Long, Drinking> drinkingIdMap = drinkingService.getAllDrinking().stream().collect(Collectors.toMap(Drinking::getId, drinking -> drinking));
        Map<Long, Smoking> smokingIdMap = smokingService.getAllSmoking().stream().collect(Collectors.toMap(Smoking::getId, smoking -> smoking));
        Map<Long, Religion> longReligionIdMap = religionService.getAllReligion().stream().collect(Collectors.toMap(Religion::getId, religion -> religion));
        LocationList locationList = getMeetingLocationList(meeting);

        List<UserSimpleProfile> userSimpleProfileList = userService.getUserSimpleProfileList(meeting.getJoinUserIds());
        return new MeetingAggregation(
                meeting,
                userSimpleProfileList,
                locationList,
                drinkingIdMap,
                smokingIdMap,
                longReligionIdMap
        );
    }

    @Transactional
    public MeetingResponse joinUser(Long meetingId, Long userId) {
        Meeting meeting = meetingService.getMeetingEntity(meetingId);
        userService.getUserById(userId);
        meeting.addUser(userId);

        if(meeting.isFull()) {
            chatRoomService.activeChatRoom(meetingId);
        }

        MeetingAggregation meetingAggregation = getMeetingAggregation(meeting);
        return MeetingResponse.from(meetingAggregation);
    }


    public MeetingResponse getMeeting(Long id) {
        Meeting meeting = meetingService.getMeetingEntity(id);

        MeetingAggregation meetingAggregation = getMeetingAggregation(meeting);


        return MeetingResponse.from(meetingAggregation);
    }

    @Transactional(readOnly = true)
    public MeetingInfoCountResponses searchMeeting(MeetingSearchCondition searchCondition, Long userId) {
        searchKeywordService.saveSearchKeyword(searchCondition.getKeyword(), userId);
        return meetingService.searchMeetingWithTotalCount(searchCondition);
    }


    public void deleteUserMeeting(Long userId) {
        List<Meeting> meetings = meetingService.getAllNotEndMeetingByUserId(userId);

        for(Meeting meeting: meetings) {
            userMeetingOut(meeting.getId(), userId);
        }
        meetingService.saveAll(meetings);
        joinMeetingService.rejectAllJoinMeeting(userId);
    }


    public MeetingResponse userMeetingOut(Long meetingId, Long userId) {
        Meeting meeting = meetingService.getMeetingEntity(meetingId);

        if(meeting.isLeader(userId) && ! meeting.isAlone()) {
            List<JoinMeeting> acceptedJoinMeeting = joinMeetingService.getAcceptedJoinMeeting(meetingId);
            List<UserDetail> userDetails = userDetailService.getUserDetails(acceptedJoinMeeting.stream().map(JoinMeeting::getUserId).toList());
            UserDetail leaderUserDetail = userDetailService.getUserDetail(meeting.getLeaderUserId());
            NextMeetingLeader nextMeetingLeader = new NextMeetingLeader(acceptedJoinMeeting, userDetails, leaderUserDetail);
            nextMeetingLeader.getNextLeaderUserId();
            meeting.changeLeader(nextMeetingLeader.getNextLeaderUserId());
        }

        meeting.outUser(userId);
        if(meeting.isEmpty()) {
            meeting.archive();
        }
        Meeting saveMeeting = meetingService.save(meeting);
        MeetingAggregation meetingAggregation = getMeetingAggregation(saveMeeting);
        return MeetingResponse.from(meetingAggregation);
    }

}
