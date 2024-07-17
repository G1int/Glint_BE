package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.MeetingInfo;

import java.util.List;

public record MeetingInfoResponses(
        List<MeetingInfoResponse> meetingResponses
) {

    public static MeetingInfoResponses from(List<MeetingInfo> meetingInfos) {
        return new MeetingInfoResponses(meetingInfos.stream().map(MeetingInfoResponse::from).toList());
    }
}
