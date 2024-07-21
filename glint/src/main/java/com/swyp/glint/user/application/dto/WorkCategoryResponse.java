package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.WorkCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkCategoryResponse {
    @Schema(description = "Work Category ID", example = "1")
    Long workCategoryId;
    @Schema(description = "Work Category Name", example = "1")
    String workCategoryName;

    public static WorkCategoryResponse from(WorkCategory workCategory) {
        if(workCategory == null) {
            return null;
        }
        return WorkCategoryResponse.builder()
                .workCategoryId(workCategory.getId())
                .workCategoryName(workCategory.getWorkCategoryName())
                .build();
    }
}
