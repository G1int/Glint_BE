package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.user.application.dto.UserProfileResponse;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.usecase.GetUserProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {

    private final UserProfileService userProfileService;


    @Override
    public UserProfileResponse getUserProfileBy(Long userId) {
        return UserProfileResponse.from(userProfileService.getUserProfileBy(userId));
    }

}