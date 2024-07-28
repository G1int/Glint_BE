package com.swyp.glint.meeting.application;


import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;



    public Meeting getMeetingEntity(Long id) {
        return meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("Not found meeting id : " + id));
    }

    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public MeetingInfoResponses getMyMeeting(Long userId, String meetingStatus, Long lastMeetingId, Integer limit) {
        return MeetingInfoResponses.from(meetingRepository.findAllMeetingInfoByStatus(userId, meetingStatus, lastMeetingId, limit));
    }


    public MeetingInfoResponses getNewMeeting(Long lastId, Integer size) {
        return MeetingInfoResponses.from(meetingRepository.findAllNotFinishMeeting(lastId, size));
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



}
