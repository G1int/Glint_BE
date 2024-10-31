package com.swyp.glint.meeting.application.service;


import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingDetail;
import com.swyp.glint.meeting.domain.MeetingInfo;
import com.swyp.glint.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public Meeting getMeeting(Long id) {
        return meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + id));
    }

    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public List<MeetingInfo> getMyMeeting(Long userId, String meetingStatus, Long lastMeetingId, Integer limit) {
        return meetingRepository.findAllMeetingInfoByStatus(userId, meetingStatus, lastMeetingId, limit);
    }

    public List<MeetingInfo> getNewMeeting(Long lastId, Integer size) {
        return meetingRepository.findAllNotFinishMeeting(lastId, size);
    }


    public List<Meeting> getAllNotEndMeetingByUserId(Long userId) {
        return meetingRepository.findAllNotEndMeetingByUserId(userId);
    }

    public List<Meeting> saveAll(List<Meeting> meetings) {
        return meetingRepository.saveAll(meetings);
    }

    public MeetingInfoCountResponses searchMeetingWithTotalCount(MeetingSearchCondition meetingSearchCondition) {
        return meetingRepository.searchMeetingWithTotalCount(meetingSearchCondition);
    }

    public MeetingDetail getMeetingDetail(Long meetingId) {
        return meetingRepository.findMeetingDetail(meetingId)
                .orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + meetingId));
    }


}
