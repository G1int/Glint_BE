package com.swyp.glint.keyword.domain;

import lombok.Getter;

@Getter
public enum UniCategory {
    PRESTIGIOUS_UNI("명문대"),
    OTHER_UNI("대학생");

    private final String description;

    UniCategory(String description) {
        this.description = description;
    }

}
