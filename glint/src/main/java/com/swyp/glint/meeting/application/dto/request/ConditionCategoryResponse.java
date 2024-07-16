package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.ConditionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ConditionCategoryResponse(
        Long id,
        String name,
        String type
) {
    public static ConditionCategoryResponse from(ConditionCategory conditionCategory) {
        return new ConditionCategoryResponse(
                conditionCategory.getId(),
                conditionCategory.getName(),
                conditionCategory.getType()
        );
    }
}
