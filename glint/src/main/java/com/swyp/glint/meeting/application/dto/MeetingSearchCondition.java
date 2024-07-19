package com.swyp.glint.meeting.application.dto;

import lombok.Data;

@Data
public class MeetingSearchCondition {

    private String title;


    public MeetingSearchCondition(String title) {
        this.title = title;
    }
}
