package com.swyp.glint.user.application.dto;


import com.swyp.glint.user.domain.User;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserRequest(
        @Parameter(description = "User Email", example = "glint@gmail.com", required = true)
        String email,
        @Parameter(description = "User Role, (OAUTH_USER)", example = "OAUTH_USER", required = true)
        String role,
        @Parameter(description = "User Provider", example = "KAKAO", required = true)
        String provider
) {

    public User toEntity() {
        return User.createNewUser(email, role, provider);
    }


    public static UserRequest of(String email, String role, String provider) {
        return UserRequest.builder()
                .email(email)
                .role(role)
                .provider(provider)
                .build();
    }

}
