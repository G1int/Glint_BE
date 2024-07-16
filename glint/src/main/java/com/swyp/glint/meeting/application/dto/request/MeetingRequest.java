package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.Meeting;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record MeetingRequest(
        @Parameter(description = "Meeting 제목", example = "다모여라", required = true)
        String title,
        @Parameter(description = "Meeting 설명", example = "모두 모여모라", required = true)
        String description,
        @Parameter(description = "미팅장 UserId", example = "1", required = true)
        Long leaderUserId,
        String location,
        List<String> selectConditionCategoryNames,
        @Parameter(description = "동성 참가조건")
        JoinConditionRequest ourConditions,
        @Parameter(description = "이성 참가조건")
        JoinConditionRequest otherConditions,
        @Parameter(description = "정원")
        Integer peopleCapacity
) {
        public Meeting toEntity() {
                return Meeting.createNewMeeting(
                        title,
                        description,
                        leaderUserId,
                        location,
                        ourConditions.toEntity(),
                        otherConditions.toEntity(),
                        peopleCapacity
                );
        }


}
