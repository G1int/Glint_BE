package com.swyp.glint.keyword.application.dto;

import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.user.application.dto.WorkResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkListResponse(
        @Schema(description = "직업 리스트")
        List<WorkResponse> works
) {
    public static WorkListResponse from(List<Work> workList) {
        if(workList == null) return null;
        List<WorkResponse> WorkResponses = workList.stream()
                .map(work -> WorkResponse.from(work))
                .toList();
        return new WorkListResponse(WorkResponses);
    }
}
