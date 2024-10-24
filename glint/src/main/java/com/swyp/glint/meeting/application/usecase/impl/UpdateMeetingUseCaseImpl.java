package com.swyp.glint.meeting.application.usecase.impl;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.service.MeetingService;
import com.swyp.glint.meeting.application.usecase.UpdateMeetingUseCase;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.UserMeetingValidator;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdateMeetingUseCaseImpl implements UpdateMeetingUseCase {

    private final MeetingService meetingService;
    private final UserProfileService userProfileService;
    private final UserDetailService userDetailService;


    @Transactional
    @Override
    public MeetingDetailResponse updateMeeting(Long meetingId, MeetingRequest meetingRequest) {
        Meeting updateRequestMeeting = meetingRequest.toEntity();
        Meeting foundMeeting = meetingService.getMeetingEntity(meetingId);

        // waiting 상태일때만 변경이 가능
        if(foundMeeting.isUnableUpdatable()) {
            throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
        }
        // 현재 참기인원, 변경 참가인원 체크
        // 참가인원 조건 validation
        List<Long> joinUserIds = foundMeeting.getJoinUserIds();
        Map<Long, UserProfile> userProfileByIdMap = userProfileService.getUserProfileByIds(joinUserIds).stream().collect(Collectors.toMap(UserProfile::getUserId, userProfile -> userProfile));
        Map<Long, UserDetail> userDetails = userDetailService.getUserDetails(joinUserIds).stream().collect(Collectors.toMap(UserDetail::getUserId, userDetail -> userDetail));
        UserDetail leaderUserDetail = userDetailService.getUserDetailBy(foundMeeting.getLeaderUserId());

        for(Long userId : joinUserIds) {
            UserProfile userProfile = userProfileByIdMap.get(userId);
            UserDetail userDetail = userDetails.get(userId);

            UserMeetingValidator userMeetingValidator = new UserMeetingValidator(userProfile, userDetail ,leaderUserDetail, updateRequestMeeting);

            if(!userMeetingValidator.isValidate()) {
                throw new InvalidValueException(ErrorCode.NOT_MATCH_CONDITION);
            }
        }

        foundMeeting.update(updateRequestMeeting);
        meetingService.save(foundMeeting);

        return MeetingDetailResponse.from(meetingService.getMeetingDetail(foundMeeting.getId()));
    }
}
