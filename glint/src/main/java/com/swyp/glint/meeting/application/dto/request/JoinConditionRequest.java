package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.AgeRange;
import com.swyp.glint.meeting.domain.HeightRange;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;

import java.util.List;


public record JoinConditionRequest(
        @Parameter(description = "선택한 참가 조건", example = "[나이, 키]")
        List<String> selectConditions,
        @Parameter(description = "회사, 학교", example = "[삼성전자, 서울대학교]")
        List<String> affiliation,
        @Parameter(description = "최소 나이", example = "20")
        @Pattern(regexp = "^[0-9]{2}$")
        Integer minAge,
        @Parameter(description = "최대 나이", example = "30")
        @Pattern(regexp = "^[0-9]{2}$")
        Integer maxAge,
        @Parameter(description = "최소 키", example = "120")
        @Pattern(regexp = "^[0-9]{3}$")
        Integer maxHeight,
        @Parameter(description = "최대 키", example = "200")
        @Pattern(regexp = "^[0-9]{3}$")
        Integer minHeight,
        @Parameter(description = "종교", example = "기독교")
        String religion,
        @Parameter(description = "흡연", example = "비흡연")
        String smoking,
        @Parameter(description = "음주", example = "마시지 않음")
        String drinking
) {

        public JoinConditionElement toEntity() {
                return JoinConditionElement.createNew(
                        selectConditions,
                        affiliation,
                        AgeRange.createNew(minAge, maxAge),
                        HeightRange.createNew(minHeight, maxHeight),
                        religion,
                        smoking,
                        drinking
                );
        }
//
//
//        public List<JoinConditionValue> toEntity() {
//                return conditionValues.stream().map(ConditionValueRequest::toEntity).toList();
//        }

}
