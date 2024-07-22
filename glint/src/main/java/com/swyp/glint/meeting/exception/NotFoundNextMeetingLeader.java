package com.swyp.glint.meeting.exception;

import com.swyp.glint.common.exception.BusinessException;
import com.swyp.glint.common.exception.ErrorCode;

public class NotFoundNextMeetingLeader extends BusinessException {

    public NotFoundNextMeetingLeader(String message) {
        super(message, ErrorCode.NOT_FOUND_NEXT_MEETING_LEADER);
    }
}