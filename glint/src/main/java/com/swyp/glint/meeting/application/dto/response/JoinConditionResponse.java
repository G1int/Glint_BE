package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.JoinConditionElement;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;

import java.util.List;

@Builder
public record JoinConditionResponse(
        @Parameter(description = "선택한 참가 조건", example = "[나이, 키]")
        List<String> selectConditions,
        @Parameter(description = "회사, 학교", example = "[삼성전자, 서울대학교]")
        List<String> affilation,
        @Parameter(description = "최소 나이", example = "20")
        Integer minAge,
        @Parameter(description = "최대 나이", example = "30")
        Integer maxAge,
        @Parameter(description = "최소 키", example = "120")
        Integer maxHeight,
        @Parameter(description = "최대 키", example = "200")
        Integer minHeight,
        @Parameter(description = "종교", example = "기독교")
        String religion,
        @Parameter(description = "흡연", example = "비흡연")
        String smoking,
        @Parameter(description = "음주", example = "마시지 않음")
        String drinking
) {

    public static JoinConditionResponse from(JoinConditionElement joinCondition) {
        return JoinConditionResponse.builder()
                .selectConditions(joinCondition.getSelectConditions())
                .affilation(joinCondition.getAffiliation())
                .minAge(joinCondition.getAgeRange().getMinAge())
                .maxAge(joinCondition.getAgeRange().getMaxAge())
                .maxHeight(joinCondition.getHeightRange().getMaxHeight())
                .minHeight(joinCondition.getHeightRange().getMinHeight())
                .religion(joinCondition.getReligion())
                .smoking(joinCondition.getSmoking())
                .drinking(joinCondition.getDrinking())
                .build();
    }
}
