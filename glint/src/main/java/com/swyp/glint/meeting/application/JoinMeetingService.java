package com.swyp.glint.meeting.application;

import com.swyp.glint.common.exception.ErrorCode;
import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingResponse;
import com.swyp.glint.meeting.application.dto.response.UserJoinMeetingResponse;
import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.meeting.domain.JoinStatus;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.UserMeetingValidator;
import com.swyp.glint.meeting.repository.JoinMeetingRepository;
import com.swyp.glint.user.application.UserDetailService;
import com.swyp.glint.user.application.UserFacade;
import com.swyp.glint.user.application.UserProfileService;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinMeetingService {

    private final JoinMeetingRepository joinMeetingRepository;

    private final MeetingService meetingService;
    private final UserProfileService userProfileService;
    private final UserDetailService userDetailService;
    private final UserFacade userFacade;

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

        JoinMeeting joinMeeting = joinMeetingRepository.save(JoinMeeting.createByRequest(userId, meetingId));
        return JoinMeetingResponse.from(joinMeeting);
    }

    @Transactional
    public JoinMeetingResponse acceptJoinMeeting(Long userId, Long meetingId) {
        JoinMeeting joinMeeting = joinMeetingRepository.findByUserAndMeeting(userId, meetingId, JoinStatus.WAITING.getName()).orElseThrow(() -> new NotFoundEntityException("Not found join meeting."));
        joinMeeting.accept();
        meetingService.joinUser(meetingId, userId);
        joinMeetingRepository.save(joinMeeting);
        return JoinMeetingResponse.from(joinMeeting);
    }

    @Transactional
    public JoinMeetingResponse rejectJoinMeeting(Long userId, Long meetingId) {
        JoinMeeting joinMeeting = joinMeetingRepository.findByUserAndMeeting(userId, meetingId, JoinStatus.WAITING.getName()).orElseThrow(() -> new NotFoundEntityException("Not found join meeting."));
        joinMeeting.reject();
        joinMeetingRepository.save(joinMeeting);
        return JoinMeetingResponse.from(joinMeeting);
    }


    public UserJoinMeetingResponses getAllJoinMeeting(Long meetingId, Long lastJoinMeetingId) {
        List<JoinMeeting> joinMeetings = Optional.ofNullable(lastJoinMeetingId)
                .map(id -> joinMeetingRepository.findByMeetingId(meetingId, lastJoinMeetingId))
                .orElseGet(() -> joinMeetingRepository.findByMeetingId(meetingId, 0L));

        List<UserJoinMeetingResponse> userJoinMeetingResponseList = joinMeetings.stream()
                        .map(joinMeeting -> UserJoinMeetingResponse.from(joinMeeting.getId(), userFacade.getUserSimpleProfile(joinMeeting.getUserId()))
                        ).toList();

        return UserJoinMeetingResponses.from(userJoinMeetingResponseList);
    }

    public UserJoinMeetingResponses getAllJoinMeetingPaging(Long meetingId, Long lastJoinMeetingId) {
        List<JoinMeeting> joinMeetings = Optional.ofNullable(lastJoinMeetingId)
                .map(id -> joinMeetingRepository.findByMeetingId(meetingId, lastJoinMeetingId))
                .orElseGet(() -> joinMeetingRepository.findByMeetingId(meetingId, 0L));

        List<UserJoinMeetingResponse> userJoinMeetingResponseList = joinMeetings.stream()
                .map(joinMeeting -> UserJoinMeetingResponse.from(joinMeeting.getId(), userFacade.getUserSimpleProfile(joinMeeting.getUserId()))
                ).toList();

        return UserJoinMeetingResponses.from(userJoinMeetingResponseList);
    }


}
