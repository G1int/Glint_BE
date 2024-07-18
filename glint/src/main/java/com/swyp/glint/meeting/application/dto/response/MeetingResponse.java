package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record MeetingResponse(
        @Schema(description = "미팅 ID", example = "1")
        Long id,
        @Schema(description = "리더 유저 ID", example = "1")
        Long leaderUserId,
        @Schema(description = "미팅 제목", example = "스타트업 미팅")
        String title,
        @Schema(description = "미팅 설명", example = "스타트업 미팅입니다.")
        String description,
        @Schema(description = "참가자 목록")
        List<UserMeetingResponse> users,
        @Schema(description = "미팅 장소", example = "[서울 전체]")
        List<String> locations,
        @Schema(description = "남성 참가 조건")
        JoinConditionResponse manCondition,
        @Schema(description = "여성 참가 조건")
        JoinConditionResponse womanCondition,
        @Schema(description = "성별당 참가 인원", example = "4")
        Integer peopleCapacity,
        @Schema(description = "미팅 상태", example = "WAITING")
        String status
) {




    public static MeetingResponse from(Meeting meeting, List<UserMeetingResponse> userMeetingResponses, List<String> locations) {
        return MeetingResponse.builder()
                .id(meeting.getId())
                .leaderUserId(meeting.getLeaderUserId())
                .title(meeting.getTitle())
                .description(meeting.getDescription())
                .users(userMeetingResponses)
                .locations(locations)
                .manCondition(JoinConditionResponse.from(meeting.getManCondition()))
                .womanCondition(JoinConditionResponse.from(meeting.getWomanCondition()))
                .peopleCapacity(meeting.getPeopleCapacity())
                .status(meeting.getStatus())
                .build();
    }
}
