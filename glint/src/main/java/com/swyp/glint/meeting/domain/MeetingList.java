package com.swyp.glint.meeting.domain;

import java.util.List;

public class MeetingList {
    private final List<Meeting> meetings;

    public MeetingList(List<Meeting> meetings) {
        this.meetings = meetings;
    }


    public void allOutMeeting(Long userId) {
        meetings.forEach(meeting -> meeting.outUser(userId));
    }


    public List<Meeting> getMeetings() {
        return meetings;
    }

}
