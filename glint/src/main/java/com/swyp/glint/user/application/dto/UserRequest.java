package com.swyp.glint.user.application.dto;


import com.swyp.glint.user.domain.User;
import lombok.Builder;

@Builder
public record UserRequest(
        String email,
        String role,
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
