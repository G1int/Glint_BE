package com.swyp.glint.meeting.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingSearchCondition {

    private String keyword;
    private Long lastMeetingId;
    private Integer limit;


}
