package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.Meeting;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record MeetingRequest(
        @Schema(description = "Meeting 제목", example = "다모여라", nullable = false)
        String title,
        @Schema(description = "Meeting 설명", example = "모두 모여모라", nullable = false)
        String description,
        @Schema(description = "미팅장(개설한 멤버) UserId", example = "1", nullable = false)
        Long leaderUserId,
        @Schema(description = "선택한 지역 Id", example = "[1,2]", nullable = true)
        List<Long> locationIds,
        @Schema(description = "동성 참가조건", nullable = true)
        JoinConditionRequest maleConditions,
        @Schema(description = "이성 참가조건", nullable = true)
        JoinConditionRequest femaleConditions,
        @Schema(description = "정원", nullable = false)
        @Pattern(regexp = "^[1-9]$")
        Integer peopleCapacity
) {
        public Meeting toEntity() {
                return Meeting.createNewMeeting(
                        title,
                        description,
                        leaderUserId,
                        locationIds,
                        maleConditions.toEntity(),
                        femaleConditions.toEntity(),
                        peopleCapacity
                );
        }


}
