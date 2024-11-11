package com.swyp.glint.meeting.application.usecase.impl;

import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.service.JoinMeetingService;
import com.swyp.glint.meeting.application.service.MeetingService;
import com.swyp.glint.meeting.application.usecase.UserMeetingUseCase;
import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingLeaderSelector;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.domain.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMeetingUseCaseImpl implements UserMeetingUseCase {

    private final MeetingService meetingService;
    private final JoinMeetingService joinMeetingService;
    private final UserDetailService userDetailService;


    @Transactional
    @Override
    public MeetingDetailResponse userMeetingOut(Long meetingId, Long userId) {
        Meeting meeting = meetingService.getMeeting(meetingId);

        if(meeting.isLeader(userId) && meeting.isJoinedUser()) {
            List<JoinMeeting> acceptedJoinMeeting = joinMeetingService.getAcceptedJoinMeeting(meetingId);
            List<UserDetail> userDetails = userDetailService.getUserDetails(meeting.getJoinUserIds());
            UserDetail leaderUserDetail = userDetailService.getUserDetailBy(meeting.getLeaderUserId());

            MeetingLeaderSelector meetingLeaderSelector = new MeetingLeaderSelector(acceptedJoinMeeting, userDetails, leaderUserDetail);
            meeting.changeLeader(meetingLeaderSelector.getNextLeaderUserId());
        }

        meeting.outUser(userId);
        meetingService.save(meeting);

        return MeetingDetailResponse.from(meetingService.getMeetingDetail(meeting.getId()));
    }


    @Override
    public void deleteUserMeeting(Long userId) {
        List<Meeting> meetings = meetingService.getAllNotEndMeetingByUserId(userId);

        for(Meeting meeting: meetings) {
            userMeetingOut(meeting.getId(), userId);
        }
        meetingService.saveAll(meetings);
        joinMeetingService.rejectAllJoinMeeting(userId);
    }
}


