package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserResponse;

public interface GetUserUseCase {
    UserResponse getUserBy(Long id);

    UserResponse getUserBy(String email);
}
