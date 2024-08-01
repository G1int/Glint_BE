package com.swyp.glint.meeting.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import com.swyp.glint.meeting.domain.JoinMeeting;
import com.swyp.glint.meeting.domain.JoinStatus;
import com.swyp.glint.meeting.repository.JoinMeetingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoinMeetingService {

    private final JoinMeetingRepository joinMeetingRepository;


    @Transactional
    public JoinMeetingResponse rejectJoinMeeting(Long userId, Long meetingId) {
        JoinMeeting joinMeeting = joinMeetingRepository.findByUserAndMeeting(userId, meetingId, JoinStatus.WAITING.getName()).orElseThrow(() -> new NotFoundEntityException("Not found join meeting."));
        joinMeeting.reject();
        joinMeetingRepository.save(joinMeeting);
        return JoinMeetingResponse.from(joinMeeting);
    }

    public List<JoinMeeting> getAllJoinMeetingEntity(Long meetingId, Long lastJoinMeetingId) {
        return joinMeetingRepository.findAllByMeetingId(meetingId, lastJoinMeetingId);
    }

    public List<JoinMeeting> getAcceptedJoinMeeting(Long meetingId) {
        return joinMeetingRepository.findByMeetingIdAndStatus(meetingId, JoinStatus.ACCEPTED.getName());
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

    @Transactional
    public void rejectAllJoinMeeting(Long userId) {
        List<JoinMeeting> joinMeetings = joinMeetingRepository.findAllByUserId(userId);
        joinMeetings.forEach(JoinMeeting::reject);
        joinMeetingRepository.saveAll(joinMeetings);
    }

    public List<Long> getJoinMeetingUserIds(Long meetingId) {
        return joinMeetingRepository.findByMeetingIdAndStatus(meetingId, JoinStatus.WAITING.getName())
                .stream()
                .map(JoinMeeting::getUserId)
                .toList();
    }
}

