package com.swyp.glint.meeting.application.usecase.impl;

import com.swyp.glint.chatting.application.ChatRoomService;
import com.swyp.glint.chatting.domain.ChatRoom;
import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.keyword.application.DrinkingService;
import com.swyp.glint.keyword.application.LocationService;
import com.swyp.glint.keyword.application.ReligionService;
import com.swyp.glint.keyword.application.SmokingService;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.service.JoinMeetingService;
import com.swyp.glint.meeting.application.service.MeetingService;
import com.swyp.glint.meeting.application.usecase.CreateMeetingUseCase;
import com.swyp.glint.meeting.domain.LocationList;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingDetail;
import com.swyp.glint.meeting.domain.UserMeetingValidator;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.service.UserSimpleProfileService;
import com.swyp.glint.user.domain.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Component
@RequiredArgsConstructor
public class CreateMeetingUseCaseImpl implements CreateMeetingUseCase {

    private final MeetingService meetingService;

    private final DrinkingService drinkingService;
    private final SmokingService smokingService;
    private final ReligionService religionService;
    private final LocationService locationService;
    private final UserSimpleProfileService userSimpleProfileService;
    private final JoinMeetingService joinMeetingService;

    private final UserDetailService userDetailService;
    private final UserProfileService userProfileService;
    private final ChatRoomService chatRoomService;

    @Transactional
    @Override
    public MeetingDetailResponse createMeeting(MeetingRequest meetingRequest) {
        Meeting meeting = meetingRequest.toEntity();
        Meeting savedMeeting = meetingService.save(meeting);
        joinMeetingService.createMeetingJoin(meetingRequest.leaderUserId(), savedMeeting.getId());
        activeChatRoom(savedMeeting.getId(), meeting.getJoinUserIds());

        Long leaderUserId = meetingRequest.leaderUserId();
        validateUserMeetingCondition(leaderUserId, meeting);

        MeetingDetail meetingDetail = MeetingDetail.of(
                meeting,
                userSimpleProfileService.getUserSimpleProfileList(meeting.getJoinUserIds()),
                getMeetingLocationList(savedMeeting),
                drinkingService.getAllDrinking(),
                smokingService.getAllSmoking(),
                religionService.getAllReligion(),
                List.of()
        );

        return MeetingDetailResponse.from(meetingDetail);
    }

    public LocationList getMeetingLocationList(Meeting meeting) {
        List<Location> locations = locationService.getLocationsByIds(meeting.getLocationIds());
        return new LocationList(locations);
    }


    public void validateUserMeetingCondition(Long leaderUserId, Meeting meeting) {
        UserDetail leaderUserDetail = userDetailService.getUserDetailBy(leaderUserId);

        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(
                userProfileService.getUserProfileBy(leaderUserId),
                leaderUserDetail,
                leaderUserDetail,
                meeting);

        if(userMeetingValidator.isInvalid()) {
            throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
        }
    }

    public void activeChatRoom(Long meetingId, List<Long> joinUserIds) {
        ChatRoom chatRoom = chatRoomService.findBy(meetingId)
                .orElseGet(() -> chatRoomService.save(ChatRoom.createByMeeting(meetingId, joinUserIds)));

        chatRoom.updateJoinUsers(joinUserIds);
        chatRoom.activate();

        chatRoomService.save(chatRoom);
    }
}
