package com.swyp.glint.user.api.dto;


import com.swyp.glint.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email
){

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
