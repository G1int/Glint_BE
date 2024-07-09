package com.swyp.glint.keyword.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
public class Work {

    private String workName;
    private Long workCategoryId;

}
