package com.swyp.glint.keyword.domain;

import lombok.Getter;

@Getter
public enum Drinking {
    NOT_DRINK("마시지 않음"),
    RARELY_DRINK("가끔 마심"),
    SOMETIMES_DRINK("어느정도 즐김"),
    LIKE_DRINK("좋아하는 편");

    private final String description;

    Drinking(String description) {
        this.description = description;
    }
}
