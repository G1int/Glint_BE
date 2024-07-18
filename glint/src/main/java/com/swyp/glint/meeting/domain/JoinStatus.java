package com.swyp.glint.meeting.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
