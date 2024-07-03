package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.User;
import lombok.Builder;

@Builder
public record UserLoginResponse(
        Long id,
        String email
) {

        public static UserLoginResponse from(User user) {
            return UserLoginResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .build();
        }
}
