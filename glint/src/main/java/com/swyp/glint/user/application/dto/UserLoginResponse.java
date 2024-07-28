package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserLoginResponse(
        @Schema(description = "User ID", example = "1", required = true)
        Long id,
        @Schema(description = "User Email", example = "glint@gmail.com", required = true)
        String email,
        @Schema(description = "기존회원, 회원가입 여부", example = "true", required = true)
        Boolean isCompleteDetail
) {

        public static UserLoginResponse from(User user, Boolean isCompleteDetail) {
            return UserLoginResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .isCompleteDetail(isCompleteDetail)
                    .build();
        }
}
