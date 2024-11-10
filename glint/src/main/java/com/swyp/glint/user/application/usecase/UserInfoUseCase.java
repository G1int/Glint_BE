package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserInfoResponse;

public interface UserInfoUseCase {
    UserInfoResponse getUserInfoBy(Long userId);
}
