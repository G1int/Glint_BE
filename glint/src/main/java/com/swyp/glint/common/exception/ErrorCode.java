package com.swyp.glint.common.exception;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    DATETIME_PARSE_WRONG(400, "C007", "DateTime Parse is Wrong"),
    UNEXPECTED_TYPE(400, "C008", "Request Param Type is Wrong"),

    // user
    EMAIL_DUPLICATION(400, "U001", "Email is Duplication"),
    NICKNAME_INVALID(400, "U002", "NickName is Invalid"),
    NICKNAME_DUPLICATED(400, "U003", "NickName is Duplicated"),
    LOGIN_INPUT_INVALID(400, "U004", "Login input is invalid"),

    // social
    INVALID_SOCIAL_TYPE(400, "S001", "Invalid Social Type"),


    //meeting
    NUMBER_OF_PEOPLE(400, "M001", "Over Number of people"),
    NOT_MATCH_CONDITION(400, "M002", "Not Match Meeting Condition"),
    NOT_FOUND_NEXT_MEETING_LEADER(400, "M003", "Not Found Next Meeting Leader"),

    //chatting
    NOT_FOUND_CHAT_ROOM(400, "CH001", "Not Found Chat Room"),
    ;


    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}
