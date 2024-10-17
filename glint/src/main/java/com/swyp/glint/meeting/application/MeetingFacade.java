package com.swyp.glint.meeting.application;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.core.common.exception.NotFoundEntityException;
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
import com.swyp.glint.meeting.exception.AlreadyJoinMeetingException;
import com.swyp.glint.meeting.exception.NumberOfPeopleException;
import com.swyp.glint.meeting.repository.JoinMeetingRepository;
import com.swyp.glint.meeting.repository.MeetingRepository;
import com.swyp.glint.searchkeyword.application.SearchKeywordService;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.service.UserSimpleProfileService;
import com.swyp.glint.user.application.service.impl.UserServiceImpl;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MeetingFacade {

    private final MeetingService meetingService;
    private final ChatRoomService chatRoomService;
    private final JoinMeetingService joinMeetingService;

    private final UserProfileService userProfileService;
    private final UserDetailService userDetailService;
    private final UserServiceImpl userService;
    private final DrinkingService drinkingService;
    private final SmokingService smokingService;
    private final ReligionService religionService;
    private final LocationService locationService;
    private final SearchKeywordService searchKeywordService;
    private final MeetingRepository meetingRepository;
    private final JoinMeetingRepository joinMeetingRepository;

    private final UserSimpleProfileService userSimpleProfileService;


    @Transactional
    public MeetingResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();

        Map<Long, Drinking> drinkingIdMap = drinkingService.getAllDrinking().stream().collect(Collectors.toMap(Drinking::getId, drinking -> drinking));
        Map<Long, Smoking> smokingIdMap = smokingService.getAllSmoking().stream().collect(Collectors.toMap(Smoking::getId, smoking -> smoking));
        Map<Long, Religion> longReligionIdMap = religionService.getAllReligion().stream().collect(Collectors.toMap(Religion::getId, religion -> religion));

        Meeting savedMeeting = meetingService.save(meeting);
        LocationList locationList = getMeetingLocationList(savedMeeting);

        List<UserSimpleProfile> userSimpleProfileList = userSimpleProfileService.getUserSimpleProfileList(meeting.getJoinUserIds());

        joinMeetingService.createMeetingJoin(meetingRequest.leaderUserId(), savedMeeting.getId());

        chatRoomService.activeChatRoom(savedMeeting.getId(), meeting.getJoinUserIds());

        Long leaderUserId = meetingRequest.leaderUserId();
        validateUserMeetingCondition(leaderUserId, meeting);

        MeetingAggregation meetingAggregation = new MeetingAggregation(
                meeting,
                userSimpleProfileList,
                locationList,
                drinkingIdMap,
                smokingIdMap,
                longReligionIdMap,
                List.of()
        );

        return MeetingResponse.from(meetingAggregation);
    }

    public LocationList getMeetingLocationList(Meeting meeting) {
        List<Location> locations = locationService.getLocationsByIds(meeting.getLocationIds());
        return new LocationList(locations);
    }

    @Transactional
    public JoinMeetingResponse joinMeetingRequest(Long userId, Long meetingId) {

        joinMeetingRepository.findByMeetingAndUserId(userId, meetingId).ifPresent(joinMeeting -> {
                throw new AlreadyJoinMeetingException("이미 참가 요청한 미팅입니다.");
        });

        Meeting meeting = meetingService.getMeetingEntity(meetingId);

        if(meeting.isJoinUser(userId)) {
            throw new AlreadyJoinMeetingException("이미 참가한 미팅입니다.");
        }

        validateUserMeetingCondition(userId, meeting);

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
        List<Long> joinMeetingUserIds = joinMeetingService.getJoinMeetingUserIds(meeting.getId());
        LocationList locationList = getMeetingLocationList(meeting);

        List<UserSimpleProfile> userSimpleProfileList = userSimpleProfileService.getUserSimpleProfileList(meeting.getJoinUserIds());
        return new MeetingAggregation(
                meeting,
                userSimpleProfileList,
                locationList,
                drinkingIdMap,
                smokingIdMap,
                longReligionIdMap,
                joinMeetingUserIds
        );
    }

    @Transactional
    public MeetingResponse joinUser(Long meetingId, Long userId) {
        Meeting meeting = meetingService.getMeetingEntity(meetingId);
        Map<String, List<UserDetail>> userGenderMap = userDetailService.getUserDetails(meeting.getJoinUserIds()).stream().collect(Collectors.groupingBy(UserDetail::getGender));
        UserDetail userDetail = userDetailService.getUserDetailBy(userId);

        //todo 리팩토링
        List<UserDetail> userGenderDetails = Optional.ofNullable(userGenderMap.get(userDetail.getGender())).orElse(List.of());
        if(userGenderDetails.size() >= meeting.getPeopleCapacity()) {
            throw new NumberOfPeopleException("인원수 초과");
        }
        userService.getUserBy(userId);
        meeting.addUser(userId);


        MeetingAggregation meetingAggregation = getMeetingAggregation(meeting);
        return MeetingResponse.from(meetingAggregation);
    }


    //todo queryDsl 전환
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


    @Transactional
    public MeetingResponse userMeetingOut(Long meetingId, Long userId) {
        Meeting meeting = meetingService.getMeetingEntity(meetingId);

        if(meeting.isLeader(userId) && meeting.isJoinedUser()) {
            List<JoinMeeting> acceptedJoinMeeting = joinMeetingService.getAcceptedJoinMeeting(meetingId);
            List<UserDetail> userDetails = userDetailService.getUserDetails(meeting.getJoinUserIds());
            UserDetail leaderUserDetail = userDetailService.getUserDetailBy(meeting.getLeaderUserId());

            NextMeetingLeader nextMeetingLeader = new NextMeetingLeader(acceptedJoinMeeting, userDetails, leaderUserDetail);
            nextMeetingLeader.getNextLeaderUserId();
            meeting.changeLeader(nextMeetingLeader.getNextLeaderUserId());
        }

        meeting.outUser(userId);
        Meeting saveMeeting = meetingService.save(meeting);
        MeetingAggregation meetingAggregation = getMeetingAggregation(saveMeeting);

        return MeetingResponse.from(meetingAggregation);
    }

    @Transactional
    public MeetingResponse updateMeeting(Long meetingId, MeetingRequest meetingRequest) {
        Meeting updateRequestMeeting = meetingRequest.toEntity();
        Meeting foundMeeting = meetingRepository.findById(meetingId).orElseThrow(() -> new NotFoundEntityException("Meeting with meetingId: " + meetingId + " not found"));

        // waiting 상태일때만 변경이 가능
        if(! foundMeeting.isUpdatable()) {
            throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
        }
        // 현재 참기인원, 변경 참가인원 체크

        // 참가인원 조건 validation
        List<Long> joinUserIds = foundMeeting.getJoinUserIds();
        Map<Long, UserProfile> userProfileByIdMap = userProfileService.getUserProfileByIds(joinUserIds).stream().collect(Collectors.toMap(UserProfile::getUserId, userProfile -> userProfile));
        Map<Long, UserDetail> userDetails = userDetailService.getUserDetails(joinUserIds).stream().collect(Collectors.toMap(UserDetail::getUserId, userDetail -> userDetail));

        for(Long userId : joinUserIds) {
            validateUserMeetingCondition(userId, updateRequestMeeting);
            UserProfile userProfile = userProfileByIdMap.get(userId);
            UserDetail userDetail = userDetails.get(userId);
            UserDetail leaderUserDetail = userDetailService.getUserDetailBy(foundMeeting.getLeaderUserId());

            UserMeetingValidator userMeetingValidator = new UserMeetingValidator(userProfile, userDetail ,leaderUserDetail, updateRequestMeeting);

            if(!userMeetingValidator.validate()) {
                throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
            }
        }

        foundMeeting.update(updateRequestMeeting);
        meetingRepository.save(foundMeeting);

        return MeetingResponse.from(getMeetingAggregation(foundMeeting));
    }



    public void validateUserMeetingCondition(Long leaderUserId, Meeting meeting) {
        UserProfile userProfile = userProfileService.getUserProfileBy(leaderUserId);
        UserDetail userDetail = userDetailService.getUserDetailBy(leaderUserId);
        UserDetail leaderUserDetail = userDetailService.getUserDetailBy(leaderUserId);

        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(userProfile, userDetail ,leaderUserDetail, meeting);

        if(!userMeetingValidator.validate()) {
            throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
        }
    }

}
