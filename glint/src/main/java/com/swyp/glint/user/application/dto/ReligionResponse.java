package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.Religion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReligionResponse {
    @Schema(description = "Religion ID", example = "1")
    Long religionId;
    @Schema(description = "종교명", example = "기독교")
    String religionName;


    public static ReligionResponse from(Religion religion) {
        return ReligionResponse.builder()
                .religionId(religion.getId())
                .religionName(religion.getReligionName())
                .build();
    }
}
