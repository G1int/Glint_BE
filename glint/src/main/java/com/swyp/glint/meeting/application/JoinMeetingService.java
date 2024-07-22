package com.swyp.glint.meeting.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import com.swyp.glint.meeting.application.dto.response.UserJoinMeetingResponse;
import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.meeting.domain.JoinStatus;
import com.swyp.glint.meeting.repository.JoinMeetingRepository;
import com.swyp.glint.user.application.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JoinMeetingService {

    private final JoinMeetingRepository joinMeetingRepository;

    private final UserFacade userFacade;


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

    public List<JoinMeeting> getAcceptedJoinMeeting(Long meetingId) {
        return joinMeetingRepository.findByMeetingIdAndStatus(meetingId, JoinStatus.ACCEPTED.getName());
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


    public JoinMeeting save(JoinMeeting joinMeeting) {
        return joinMeetingRepository.save(joinMeeting);
    }

    public JoinMeeting getUserWaitingJoinMeetingEntity(Long userId, Long meetingId) {
        return joinMeetingRepository.findByUserAndMeeting(userId, meetingId, JoinStatus.WAITING.getName())
                .orElseThrow(() -> new NotFoundEntityException("Not found join meeting."));
    }

    public JoinMeeting createMeetingJoin(Long userId, Long meetingId) {
        return joinMeetingRepository.save(JoinMeeting.createByMeetingInit(userId, meetingId));
    }
}

