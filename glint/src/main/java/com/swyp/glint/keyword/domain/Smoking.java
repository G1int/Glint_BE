package com.swyp.glint.keyword.domain;

import lombok.Getter;

@Getter
public enum Smoking {
    SMOKING("흡연"),
    NON_SMOKING("비흠연");

    private final String description;

    Smoking(String description) {
        this.description = description;
    }
}
