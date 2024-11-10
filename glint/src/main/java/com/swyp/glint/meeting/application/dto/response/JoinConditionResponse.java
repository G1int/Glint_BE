package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.MeetingJoinCondition;
import com.swyp.glint.user.application.dto.DrinkingResponse;
import com.swyp.glint.user.application.dto.ReligionResponse;
import com.swyp.glint.user.application.dto.SmokingResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record JoinConditionResponse(

        @Schema(description = "선택한 참가 조건", example = "[나이, 키]")
        List<String> selectConditions,
        @Schema(description = "회사, 학교", example = "[삼성전자, 서울대학교]")
        List<String> affiliation,
        @Schema(description = "최소 나이", example = "20")
        Integer minAge,
        @Schema(description = "최대 나이", example = "30")
        Integer maxAge,
        @Schema(description = "최소 키", example = "140")
        Integer maxHeight,
        @Schema(description = "최대 키", example = "200")
        Integer minHeight,
        @Schema(description = "종교 Id", example = "[1]")
        List<ReligionResponse> religion,
        @Schema(description = "흡연 Id", example = "[1]")
        List<SmokingResponse> smoking,
        @Schema(description = "음주 Id", example = "[1]")
        List<DrinkingResponse> drinking
) {

    public static JoinConditionResponse from(MeetingJoinCondition joinCondition) {
        return JoinConditionResponse.builder()
                .selectConditions(joinCondition.getSelectConditions())
                .affiliation(joinCondition.getAffiliation())
                .minAge(joinCondition.getAgeRange().getMinAge())
                .maxAge(joinCondition.getAgeRange().getMaxAge())
                .maxHeight(joinCondition.getHeightRange().getMaxHeight())
                .minHeight(joinCondition.getHeightRange().getMinHeight())
                .religion(joinCondition.getReligionConditions().stream().map(ReligionResponse::from).toList())
                .smoking(joinCondition.getSmokingConditions().stream().map(SmokingResponse::from).toList())
                .drinking(joinCondition.getDrinkingConditions().stream().map(DrinkingResponse::from).toList())
                .build();
    }
}
