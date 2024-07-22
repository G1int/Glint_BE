package com.swyp.glint.meeting.exception;

import com.swyp.glint.common.exception.BusinessException;
import com.swyp.glint.common.exception.ErrorCode;

public class NumberOfPeopleException extends BusinessException {
    public NumberOfPeopleException(String message) {
        super(ErrorCode.NUMBER_OF_PEOPLE);
    }
}
