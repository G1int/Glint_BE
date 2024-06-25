package com.swyp.glint.user.application;

import com.swyp.glint.user.api.dto.UserRequest;
import com.swyp.glint.user.api.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUser(Long id);
}