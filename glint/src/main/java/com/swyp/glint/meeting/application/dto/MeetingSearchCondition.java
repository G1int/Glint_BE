package com.swyp.glint.meeting.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingSearchCondition {

    @Schema(description = "검색 키워드", example = "모여")
    private String keyword;
    @Schema(description = "마지막 미팅 ID", example = "1", nullable = true)
    private Long lastMeetingId;
    @Schema(description = "조회 개수", example = "10", nullable = true)
    private Integer limit;
    @Schema(description = "지역 ID 리스트", example = "[1]", nullable = true)
    private List<Long> locationIds;
}
