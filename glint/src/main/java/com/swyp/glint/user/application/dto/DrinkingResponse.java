package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.Drinking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrinkingResponse {
    @Schema(description = "Drinking ID", example = "1")
    Long drinkingId;
    @Schema(description = "음주명", example = "마시지 않음")
    String drinkingName;


    public static DrinkingResponse from(Drinking drinking) {
        return DrinkingResponse.builder()
                .drinkingId(drinking.getId())
                .drinkingName(drinking.getDrinkingName())
                .build();
    }
}
