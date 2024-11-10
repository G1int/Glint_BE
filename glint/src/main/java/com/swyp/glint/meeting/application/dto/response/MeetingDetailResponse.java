package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.MeetingDetail;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record MeetingDetailResponse(
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
        JoinConditionResponse maleCondition,
        @Schema(description = "여성 참가 조건")
        JoinConditionResponse femaleCondition,
        @Schema(description = "성별당 참가 인원", example = "4")
        Integer peopleCapacity,
        @Schema(description = "미팅 상태", example = "WAITING")
        String status,
        @Schema(description = "참가 요청한 유저 ID 목록", example = "[1,2,3]")
        List<Long> joinRequestUserIds
) {



    public static MeetingDetailResponse from(MeetingDetail meetingDetail) {
        return MeetingDetailResponse.builder()
                .id(meetingDetail.getId())
                .leaderUserId(meetingDetail.getLeaderUserId())
                .title(meetingDetail.getTitle())
                .description(meetingDetail.getDescription())
                .users(meetingDetail.getUsers().stream().map(UserMeetingResponse::from).toList())
                .locations(meetingDetail.getLocationNames())
                .maleCondition(JoinConditionResponse.from(meetingDetail.getMaleCondition()))
                .femaleCondition(JoinConditionResponse.from(meetingDetail.getFemaleCondition()))
                .peopleCapacity(meetingDetail.getPeopleCapacity())
                .status(meetingDetail.getStatus())
                .joinRequestUserIds(meetingDetail.getJoinRequestUserIds())
                .build();
    }
}
