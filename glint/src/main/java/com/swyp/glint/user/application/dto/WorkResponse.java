package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.Work;
import com.swyp.glint.keyword.domain.WorkCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkResponse {
    @Schema(description = "Work ID", example = "1")
    Long workId;

    @Schema(description = "직업명", example = "삼성전자")
    String workName;

    WorkCategoryResponse workCategory;

    public static WorkResponse from(Work work) {
        if(work == null) return null;
        return WorkResponse.builder()
                .workId(work.getId())
                .workName(work.getWorkName())
                .workCategory(WorkCategoryResponse.from(work.getWorkCategory()))
                .build();
    }
}
