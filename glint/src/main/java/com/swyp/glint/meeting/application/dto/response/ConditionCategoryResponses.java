package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.application.dto.request.ConditionCategoryResponse;

import java.util.List;

public record ConditionCategoryResponses(
        List<ConditionCategoryResponse> conditionCategory
) {
    public static ConditionCategoryResponses from(List<ConditionCategory> conditionCategories) {
        return new ConditionCategoryResponses(
                conditionCategories.stream().map(ConditionCategoryResponse::from).toList()
        );
    }
}
