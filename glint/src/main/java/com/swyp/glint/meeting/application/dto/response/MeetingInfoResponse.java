package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.MeetingInfo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MeetingInfoResponse(
        @Schema(description = "미팅 ID", example = "1")
        Long meetingId,
        @Schema(description = "남성 참가자 수", example = "10")
        Integer maleCount,
        @Schema(description = "여성 참가자 수", example = "10")
        Integer femaleCount,
        @Schema(description = "남성 나이 범위", example = "20~30")
        AgeRangeResponse maleAgeRange,
        @Schema(description = "여성 나이 범위", example = "20~30")
        AgeRangeResponse femaleAgeRange,
        @Schema(description = "미팅 제목", example = "스타트업 미팅")
        String title,
        @Schema(description = "미팅 상태", example = "WAITING")
        String status,
        @Schema(description = "미팅 이미지", example = "https://glint.com")
        String meetingImage,
        @Schema(description = "미팅 장소", example = "[서울 전체]")
        List<String> locations,
        @Schema(description = "성별당 참가 인원", example = "4")
        Integer peopleCapacity
) {

    public static MeetingInfoResponse from(MeetingInfo meetingInfo) {
        return new MeetingInfoResponse(
                meetingInfo.getMeetingId(),
                meetingInfo.getMaleCount(),
                meetingInfo.getFemaleCount(),
                AgeRangeResponse.from(meetingInfo.getManAgeRange()),
                AgeRangeResponse.from(meetingInfo.getWomanAgeRange()),
                meetingInfo.getTitle(),
                meetingInfo.getStatus(),
                meetingInfo.getMeetingImage(),
                meetingInfo.getLocationKeywords(),
                meetingInfo.getPeopleCapacity()
        );
    }


}
