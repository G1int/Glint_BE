package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;

@Builder
public record UserLoginResponse(
        @Parameter(description = "User ID", example = "1", required = true)
        Long id,
        @Parameter(description = "User Email", example = "glint@gmail.com", required = true)
        String email
) {

        public static UserLoginResponse from(User user) {
            return UserLoginResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .build();
        }
}
