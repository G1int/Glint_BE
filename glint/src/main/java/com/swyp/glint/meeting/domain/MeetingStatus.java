package com.swyp.glint.meeting.domain;

public enum MeetingStatus {
    WAITING("WAITING"), PROGRESS("PROGRESS"), END("END");

    private String name;

    MeetingStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
