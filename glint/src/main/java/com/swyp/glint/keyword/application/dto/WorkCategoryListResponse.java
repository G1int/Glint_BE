package com.swyp.glint.keyword.application.dto;

import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.user.application.dto.WorkCategoryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record WorkCategoryListResponse(

        @Schema(description = "직업 리스트")
        List<WorkCategoryResponse> workCategories

) {
    public static WorkCategoryListResponse from(List<WorkCategory> workCategoryList) {
        if(workCategoryList == null) return null;
        List<WorkCategoryResponse> WorkCategoryResponses = workCategoryList.stream()
                .map(WorkCategoryResponse::from)
                .toList();
        return new WorkCategoryListResponse(WorkCategoryResponses);
    }
}
