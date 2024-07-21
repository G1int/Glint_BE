package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.Smoking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmokingResponse {

    @Schema(description = "Smoking ID", example = "1")
    Long smokingId;
    @Schema(description = "흡연명", example = "흡연")
    String smokingName;

    public static SmokingResponse from(Smoking smoking) {
        return SmokingResponse.builder()
                .smokingId(smoking.getId())
                .smokingName(smoking.getSmokingName())
                .build();
    }

}
