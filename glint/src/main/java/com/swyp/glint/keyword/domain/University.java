package com.swyp.glint.keyword.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
public class University {

    private String universityName;
    private Long universityCategoryId;

}