package com.swyp.glint.core.common.exception;

public class FileNotFoundException extends BusinessException {

    public FileNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}
