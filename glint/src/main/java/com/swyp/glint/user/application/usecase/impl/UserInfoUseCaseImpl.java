package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.user.application.usecase.UserInfoUseCase;
import com.swyp.glint.user.application.service.UserService;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoUseCaseImpl implements UserInfoUseCase {

    private final UserService userService;


    @Override
    public UserInfoResponse getUserInfoBy(Long userId) {
        return UserInfoResponse.from(userService.getUserInfoBy(userId));
    }



}
