package com.swyp.glint.meeting.application.dto.request;

import com.swyp.glint.meeting.domain.AgeRange;
import com.swyp.glint.meeting.domain.HeightRange;
import com.swyp.glint.meeting.domain.JoinConditionElement;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

import java.util.List;


public record JoinConditionRequest(
        @Schema(description = "선택한 참가 조건, AFFILIATION(직장,학교), AGE(나이), HEIGHT(키), RELIGION(종교), DRINKING(음주), SMOKING(흡연) ", example = "[\"AFFILIATION\", \"AGE\"]")
        List<String> selectConditions,

        @Schema(description = "회사, 학교", example = "[\"삼성전자\", \"서울대학교\"]")
        List<String> affiliation,

        @Schema(description = "최소 나이", example = "20")
        @Pattern(regexp = "^[0-9]{2}$")
        Integer minAge,

        @Schema(description = "최대 나이", example = "30")
        @Pattern(regexp = "^[0-9]{2}$")
        Integer maxAge,

        @Schema(description = "최소 키", example = "140")
        @Pattern(regexp = "^[0-9]{3}$")
        Integer maxHeight,

        @Schema(description = "최대 키", example = "200")
        @Pattern(regexp = "^[0-9]{3}$")
        Integer minHeight,

        @Schema(description = "종교", example = "[\"기독교\"]")
        List<Long> religionIds,

        @Schema(description = "흡연", example = "[\"비흡연\"]")
        List<Long> smokingIds,

        @Schema(description = "음주", example = "[\"마시지않음\"]")
        List<Long> drinkingIds
) {

        public JoinConditionElement toEntity() {
                return JoinConditionElement.createNew(
                        selectConditions,
                        affiliation,
                        AgeRange.createNew(minAge, maxAge),
                        HeightRange.createNew(minHeight, maxHeight),
                        religionIds,
                        smokingIds,
                        drinkingIds
                );
        }
//
//
//        public List<JoinConditionValue> toEntity() {
//                return conditionValues.stream().map(ConditionValueRequest::toEntity).toList();
//        }

}
