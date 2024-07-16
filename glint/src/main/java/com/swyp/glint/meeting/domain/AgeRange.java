package com.swyp.glint.meeting.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class AgeRange {

    Integer minAge;

    Integer maxAge;

    @Builder(access = lombok.AccessLevel.PRIVATE)
    private AgeRange(Integer minAge, Integer maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static AgeRange createNew(Integer minAge, Integer maxAge) {
        return AgeRange.builder()
                .minAge(minAge)
                .maxAge(maxAge)
                .build();
    }

}
