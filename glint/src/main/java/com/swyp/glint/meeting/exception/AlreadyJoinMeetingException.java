package com.swyp.glint.meeting.exception;

import com.swyp.glint.core.common.exception.BusinessException;
import com.swyp.glint.core.common.exception.ErrorCode;

public class AlreadyJoinMeetingException extends BusinessException {

    public AlreadyJoinMeetingException(String message) {
        super(message, ErrorCode.ALREADY_JOIN_MEETING);
    }

    public AlreadyJoinMeetingException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
