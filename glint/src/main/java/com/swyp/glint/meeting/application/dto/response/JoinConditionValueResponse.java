package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.JoinConditionValue;

public record JoinConditionValueResponse(
        String conditionCategoryName,
        String value,
        String type
) {

    public static JoinConditionValueResponse from(JoinConditionValue joinConditionValue) {
        return new JoinConditionValueResponse(
                joinConditionValue.getConditionCategoryName(),
                joinConditionValue.getValue(),
                joinConditionValue.getType()
        );
    }

}
