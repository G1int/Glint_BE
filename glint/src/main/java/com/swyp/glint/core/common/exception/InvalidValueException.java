package com.swyp.glint.core.common.exception;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
}
