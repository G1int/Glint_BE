package com.swyp.glint.user.repository;

import com.swyp.glint.user.application.dto.UserInfoResponse;

import java.util.Optional;

public interface UserProfileCustom {
    Optional<UserInfoResponse> findUserInfoBy(Long userId);


}
