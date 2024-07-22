package com.swyp.glint.user.application.dto;


import com.swyp.glint.user.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;

@Builder
public record UserResponse(
        @Parameter(description = "User ID", example = "1", required = true)
        Long id,
        @Parameter(description = "User Email", example = "glint@gmail.com", required = true)
        String email,
        @Parameter(description = "User Role, (OAUTH_USER)", example = "OAUTH_USER", required = true)
        String role,
        @Parameter(description = "User Provider", example = "KAKAO", required = true)
        String provider
){

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
