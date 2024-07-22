package com.swyp.glint.user.application.dto;

import io.swagger.v3.oas.annotations.Parameter;

public record UserNickNameRequest(
        @Parameter(description = "User Nickname", example = "철수", required = true)
        String nickname
) {
}
