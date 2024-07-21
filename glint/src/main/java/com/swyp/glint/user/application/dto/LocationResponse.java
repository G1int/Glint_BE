package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {

    @Schema(description = "Location ID", example = "1")
    Long locationId;
    @Schema(description = "위치의 [시,도]", example = "서울특별시")
    String locationState;
    @Schema(description = "위치의 [시,군,구]", example = "강남구")
    String locationCity;

    public static LocationResponse from(Location location) {
        if(Objects.isNull(location)) return null;
        return LocationResponse.builder()
                .locationId(location.getId())
                .locationState(location.getState())
                .locationCity(location.getCity())
                .build();
    }
}
