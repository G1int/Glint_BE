package com.swyp.glint.meeting.application.usecase.impl;

import com.swyp.glint.meeting.application.dto.response.UserJoinMeetingResponse;
import com.swyp.glint.meeting.application.dto.response.UserJoinMeetingResponses;
import com.swyp.glint.meeting.application.service.JoinMeetingService;
import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.user.application.service.UserSimpleProfileService;
import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JoinMeetingUseCaseImpl {

    private final UserSimpleProfileService userSimpleProfileService;
    private final JoinMeetingService joinMeetingService;

    public UserJoinMeetingResponses getAllJoinMeeting(Long meetingId, Long lastJoinMeetingId) {
        List<JoinMeeting> joinMeetings = Optional.ofNullable(lastJoinMeetingId)
                .map(id -> joinMeetingService.getAllJoinMeetingEntity(meetingId, lastJoinMeetingId))
                .orElseGet(() -> joinMeetingService.getAllJoinMeetingEntity(meetingId, 0L));


        Map<Long, UserSimpleProfile> userSimpleProfileIdMap =
                userSimpleProfileService.getUserSimpleProfileList(joinMeetings.stream().map(JoinMeeting::getUserId).toList()).stream()
                .collect(Collectors.toMap(UserSimpleProfile::getUserId, userSimpleProfile -> userSimpleProfile));

        return UserJoinMeetingResponses.from(
                joinMeetings.stream()
                        .map(joinMeeting -> UserJoinMeetingResponse.from(joinMeeting.getId(), userSimpleProfileIdMap.get(joinMeeting.getUserId())))
                        .toList()
        );
    }


}
