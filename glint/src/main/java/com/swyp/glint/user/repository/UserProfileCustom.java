package com.swyp.glint.user.repository;

import com.swyp.glint.user.application.dto.UserProfileWithDetailResponse;

import java.util.Optional;

public interface UserProfileCustom {
    Optional<UserProfileWithDetailResponse> findUserInfoBy(Long userId);

}
