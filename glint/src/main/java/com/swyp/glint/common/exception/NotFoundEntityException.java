package com.swyp.glint.common.exception;

public class NotFoundEntityException extends BusinessException {

    public NotFoundEntityException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
