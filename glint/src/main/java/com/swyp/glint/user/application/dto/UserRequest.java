package com.swyp.glint.user.application.dto;


import com.swyp.glint.user.domain.User;
import lombok.Builder;

@Builder
public record UserRequest(
        String name,
        String email,
        String role,
        String provider
) {

    public User toEntity() {
        return User.createNewUser(name, email, role, provider);
    }


    public static UserRequest of(String name, String email, String role, String provider) {
        return UserRequest.builder()
                .name(name)
                .email(email)
                .role(role)
                .provider(provider)
                .build();
    }

}
