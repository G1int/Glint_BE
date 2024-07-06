package com.swyp.glint.user.application.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;

@Builder
public record UserNickNameValidationResponse(
        @Parameter(description = "User Nickname", example = "철수", required = true)
        String nickname,
        @Parameter(description = "User Nickname is available", example = "true", required = true)
        Boolean isAvailable
) {

    public static UserNickNameValidationResponse from(boolean isAvaliable, String nickname) {
        return UserNickNameValidationResponse.builder()
                .nickname(nickname)
                .isAvailable(isAvaliable)
                .build();
    }
}
