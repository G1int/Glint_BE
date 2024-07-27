package com.swyp.glint.keyword.application.dto;

import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.user.application.dto.LocationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record LocationListResponse(

        @Schema(description = "위치 리스트")
        List<LocationResponse> locations

) {
    public static LocationListResponse from(List<Location> locationList) {
        if(locationList == null) return null;
        List<LocationResponse> locationResponses = locationList.stream()
                .map(location -> LocationResponse.from(location))
                .toList();
        return new LocationListResponse(locationResponses);
    }

}
