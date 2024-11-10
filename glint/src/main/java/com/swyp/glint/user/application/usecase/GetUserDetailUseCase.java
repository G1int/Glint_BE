package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserDetailResponse;

public interface GetUserDetailUseCase {
    UserDetailResponse getUserDetailBy(Long userId);
}
