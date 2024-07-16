package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.JoinConditionValue;
import jakarta.persistence.criteria.Join;

public record ConditionValueRequest(
        String conditionCategoryName,
        String value,
        String type
) {

    public JoinConditionValue toEntity() {
        return JoinConditionValue.createNew(
                conditionCategoryName,
                value,
                type
        );
    }
}
