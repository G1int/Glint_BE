package com.swyp.glint.keyword.domain;

import lombok.Getter;

@Getter
public enum WorkCategory {
    MAJOR_COMPANY("대기업"),
    GOVERNMENT_COMPANY("공기업"),
    PROFESSION("전문직"),
    GOVERNMENT_EMPLOYEE("공무원");

    private final String description;

    WorkCategory(String description) {
        this.description = description;
    }

}
