package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import jakarta.transaction.Transactional;

public interface UpdateUserProfileUseCase {
    @Transactional
    UserInfoResponse updateUserProfile(Long userId, UserProfileRequest userProfileRequest);
}
