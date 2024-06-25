package com.swyp.glint.user.api.dto;


import com.swyp.glint.user.domain.User;

public record UserRequest(
        String username,
        String email
) {

    public User toEntity() {
        return User.createNewUser(username, email);
    }

}
