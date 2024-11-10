package com.swyp.glint.meeting.application.usecase.impl;

import com.swyp.glint.keyword.application.LocationService;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoResponses;
import com.swyp.glint.meeting.application.service.MeetingService;
import com.swyp.glint.meeting.application.usecase.GetMeetingUseCase;
import com.swyp.glint.searchkeyword.application.SearchKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class GetMeetingUseCaseImpl implements GetMeetingUseCase {

    private final MeetingService meetingService;
    private final LocationService locationService;
    private final SearchKeywordService searchKeywordService;

    @Override
    @Transactional
    public MeetingDetailResponse getMeeting(Long meetingId) {
        return MeetingDetailResponse.from(meetingService.getMeetingDetail(meetingId));
    }

    @Override
    public MeetingInfoResponses getNewMeeting(Long lastId, Integer size) {
        return MeetingInfoResponses.from(meetingService.getNewMeeting(lastId, size));
    }

    @Override
    public MeetingInfoResponses getMyMeeting(Long userId, String meetingStatus, Long lastMeetingId, Integer limit) {
        return MeetingInfoResponses.from(meetingService.getMyMeeting(userId, meetingStatus, lastMeetingId, limit));
    }

    @Transactional(readOnly = true)
    @Override
    public MeetingInfoCountResponses searchMeeting(MeetingSearchCondition searchCondition, Long userId) {
        searchKeywordService.saveSearchKeyword(searchCondition.getKeyword(), userId);
        return meetingService.searchMeetingWithTotalCount(searchCondition);
    }

}
