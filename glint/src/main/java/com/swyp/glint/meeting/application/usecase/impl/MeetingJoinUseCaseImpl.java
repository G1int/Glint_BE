package com.swyp.glint.meeting.application.usecase.impl;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import com.swyp.glint.meeting.application.service.JoinMeetingService;
import com.swyp.glint.meeting.application.service.MeetingService;
import com.swyp.glint.meeting.application.usecase.MeetingJoinUseCase;
import com.swyp.glint.meeting.domain.*;
import com.swyp.glint.meeting.exception.AlreadyJoinMeetingException;
import com.swyp.glint.meeting.exception.NumberOfPeopleException;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.service.UserService;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MeetingJoinUseCaseImpl implements MeetingJoinUseCase {

    private final MeetingService meetingService;
    private final JoinMeetingService joinMeetingService;
    private final UserDetailService userDetailService;
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Transactional
    @Override
    public JoinMeetingResponse joinMeetingRequest(Long userId, Long meetingId) {

        joinMeetingService.getWaitStatusJoinMeetingRequest(userId, meetingId);

        Meeting meeting = meetingService.getMeetingEntity(meetingId);

        if(meeting.isJoinUser(userId)) {
            throw new AlreadyJoinMeetingException("이미 참가한 미팅입니다.");
        }

        validateUserMeetingCondition(userId, meeting);

        JoinMeeting joinMeeting = joinMeetingService.save(JoinMeeting.createByRequest(userId, meetingId));

        return JoinMeetingResponse.from(joinMeeting);
    }

    @Transactional
    @Override
    public JoinMeetingResponse acceptJoinMeeting(Long userId, Long meetingId) {
        JoinMeeting joinMeeting = joinMeetingService.getUserWaitingJoinMeetingEntity(userId, meetingId);
        joinMeeting.accept();
        joinUser(meetingId, userId);
        joinMeetingService.save(joinMeeting);
        return JoinMeetingResponse.from(joinMeeting);
    }

    @Transactional
    @Override
    public void joinUser(Long meetingId, Long userId) {
        Meeting meeting = meetingService.getMeetingEntity(meetingId);
        UserDetail userDetail = userDetailService.getUserDetailBy(userId);

        MeetingJoinRequestValidator meetingJoinRequestValidator = new MeetingJoinRequestValidator(userDetailService.getUserDetails(meeting.getJoinUserIds()), meeting, userDetail);

        if(!meetingJoinRequestValidator.isValidJoinUser()) {
            throw new NumberOfPeopleException("인원이 초과되었습니다.");
        }

        userService.getUserBy(userId);
        meeting.addUser(userId);
        meetingService.save(meeting);
    }


    public void validateUserMeetingCondition(Long leaderUserId, Meeting meeting) {
        UserProfile userProfile = userProfileService.getUserProfileBy(leaderUserId);
        UserDetail userDetail = userDetailService.getUserDetailBy(leaderUserId);
        UserDetail leaderUserDetail = userDetailService.getUserDetailBy(leaderUserId);

        UserMeetingValidator userMeetingValidator = new UserMeetingValidator(userProfile, userDetail ,leaderUserDetail, meeting);

        if(!userMeetingValidator.isValidate()) {
            throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
        }
    }


    public JoinMeetingResponse rejectJoinMeeting(Long userId, Long meetingId) {

        return JoinMeetingResponse.from(joinMeetingService.rejectJoinMeeting(userId, meetingId));
    }


}
