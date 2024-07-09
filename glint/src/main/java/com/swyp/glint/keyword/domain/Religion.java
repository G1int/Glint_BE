package com.swyp.glint.keyword.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Religion {
    NOT_RELIGIOUS("무교"),
    CHRISTIAN("기독교"),
    CATHOLIC("천주교"),
    BUDDHISM("불교"),
    OTHERS("기타");

    private final String description;

    Religion(String description) {
        this.description = description;
    }


}
