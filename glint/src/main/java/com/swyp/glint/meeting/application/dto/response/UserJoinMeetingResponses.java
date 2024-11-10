package com.swyp.glint.meeting.application.dto.response;

import java.util.List;

public record UserJoinMeetingResponses(
        List<UserJoinMeetingResponse> userJoinMeetings
) {

    public static UserJoinMeetingResponses from(List<UserJoinMeetingResponse> userJoinMeetings) {
        return new UserJoinMeetingResponses(userJoinMeetings);
    }

}
