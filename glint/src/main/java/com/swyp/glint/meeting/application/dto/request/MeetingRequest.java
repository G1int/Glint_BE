package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.Meeting;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MeetingRequest(
        @Schema(description = "Meeting 제목", example = "다모여라", nullable = false)
        String title,
        @Schema(description = "Meeting 설명", example = "모두 모여모라", nullable = false)
        String description,
        @Schema(description = "미팅장(개설한 멤버) UserId", example = "1", nullable = false)
        Long leaderUserId,
        @Schema(description = "선택한 지역 Id", example = "[1,2]", nullable = true)
        List<Long> locations,
        @Schema(description = "동성 참가조건", nullable = true)
        JoinConditionRequest ourConditions,
        @Schema(description = "이성 참가조건", nullable = true)
        JoinConditionRequest otherConditions,
        @Schema(description = "정원", nullable = false)
        Integer peopleCapacity
) {
        public Meeting toEntity() {
                return Meeting.createNewMeeting(
                        title,
                        description,
                        leaderUserId,
                        locations,
                        ourConditions.toEntity(),
                        otherConditions.toEntity(),
                        peopleCapacity
                );
        }


}
