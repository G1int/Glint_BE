package com.swyp.glint.meeting.application;

import com.swyp.glint.meeting.application.dto.response.UserJoinMeetingResponse;
import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.user.application.impl.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JoinMeetingFacade {

    private final UserFacade userFacade;

    private final JoinMeetingService joinMeetingService;


    public UserJoinMeetingResponses getAllJoinMeeting(Long meetingId, Long lastJoinMeetingId) {
        List<JoinMeeting> joinMeetings = Optional.ofNullable(lastJoinMeetingId)
                .map(id -> joinMeetingService.getAllJoinMeetingEntity(meetingId, lastJoinMeetingId))
                .orElseGet(() -> joinMeetingService.getAllJoinMeetingEntity(meetingId, 0L));


        return UserJoinMeetingResponses.from(
                joinMeetings.stream()
                        .map(joinMeeting -> UserJoinMeetingResponse.from(
                                        joinMeeting.getId(),
                                        userFacade.getUserSimpleProfile(joinMeeting.getUserId())
                                ))
                        .toList()
        );
    }


}
