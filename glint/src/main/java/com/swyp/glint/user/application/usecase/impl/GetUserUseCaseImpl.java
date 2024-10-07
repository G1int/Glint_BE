package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.application.service.UserService;
import com.swyp.glint.user.application.usecase.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetUserUseCaseImpl implements GetUserUseCase {

    private final UserService userService;

    @Override
    public UserResponse getUserBy(Long id) {
        return UserResponse.from(userService.getUserBy(id));
    }

    @Override
    public UserResponse getUserBy(String email) {
        return UserResponse.from(userService.getUserBy(email));
    }


}
