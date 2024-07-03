package com.swyp.glint.user.application.dto;


import com.swyp.glint.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String role,
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
