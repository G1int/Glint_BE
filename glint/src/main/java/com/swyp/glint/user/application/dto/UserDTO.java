package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.User;
import lombok.Builder;

@Builder
public record UserDTO(
        String name,
        String email,
        String role,
        String provider
) {

    public static User toEntity(UserDTO userDTO) {
        return User.createNewUser(userDTO.name(), userDTO.email(), userDTO.role(), userDTO.provider());
    }

}
