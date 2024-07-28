package com.swyp.glint.chatting.exception;

import com.swyp.glint.common.exception.BusinessException;
import com.swyp.glint.common.exception.ErrorCode;

public class NotFoundChatRoomException extends BusinessException {
    public NotFoundChatRoomException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}