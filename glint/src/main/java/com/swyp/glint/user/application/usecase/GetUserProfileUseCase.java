package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserProfileResponse;

public interface GetUserProfileUseCase {
    UserProfileResponse getUserProfileBy(Long userId);
}
