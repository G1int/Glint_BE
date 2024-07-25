package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.MeetingInfo;

import java.util.List;

public record MeetingInfoCountResponses(
        List<MeetingInfoResponse> meetings,
        Integer totalCount
) {

    public static MeetingInfoCountResponses from(List<MeetingInfo> meetingInfos, Integer totalCount) {
        return new MeetingInfoCountResponses(meetingInfos.stream().map(MeetingInfoResponse::from).toList(), totalCount);
    }
}
