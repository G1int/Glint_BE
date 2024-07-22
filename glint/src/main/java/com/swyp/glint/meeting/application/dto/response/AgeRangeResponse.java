package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.meeting.domain.AgeRange;

import java.util.Optional;

public record AgeRangeResponse(
        Integer minAge,
        Integer maxAge
) {

    public static AgeRangeResponse from(AgeRange ageRange) {
        return Optional.ofNullable(ageRange)
                .map(ageRangeOptional ->  new AgeRangeResponse(ageRangeOptional.getMinAge(), ageRangeOptional.getMaxAge()))
                .orElse(null);
    }

}
