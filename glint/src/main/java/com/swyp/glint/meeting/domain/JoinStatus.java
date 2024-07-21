package com.swyp.glint.meeting.domain;

public enum JoinStatus {
    WAITING("WAITING"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED");

    private String name;

    JoinStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
