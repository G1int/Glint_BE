package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MeetingResponse(
        Long id,
        Long leaderUserId,
        String title,
        String description,
        List<UserMeetingResponse> users,
        List<String> location,
        JoinConditionResponse ourCondition,
        JoinConditionResponse otherCondition,
        Integer peopleCapacity,
        String status
) {




    public static MeetingResponse from(Meeting meeting, List<UserMeetingResponse> userMeetingResponses) {
        return MeetingResponse.builder()
                .id(meeting.getId())
                .leaderUserId(meeting.getLeaderUserId())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .users(userMeetingResponses)
                .location(List.of("서울 전체"))
                .ourCondition(JoinConditionResponse.from(meeting.getOurCondition()))
                .otherCondition(JoinConditionResponse.from(meeting.getOtherCondition()))
                .peopleCapacity(meeting.getPeopleCapacity())
                .status(meeting.getStatus())
                .build();
    }
}
