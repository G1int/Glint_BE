package com.swyp.glint.meeting.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

@Table(name = "height_range")
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeightRange {
    Integer minHeight;
    Integer maxHeight;

    @Builder(access = AccessLevel.PRIVATE)
    private HeightRange(Integer minHeight, Integer maxHeight) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public static HeightRange createNew(Integer minHeight, Integer maxHeight) {
        return HeightRange.builder()
                .minHeight(minHeight)
                .maxHeight(maxHeight)
                .build();
    }
}
