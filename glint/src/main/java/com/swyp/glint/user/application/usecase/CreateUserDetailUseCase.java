package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import jakarta.transaction.Transactional;

public interface CreateUserDetailUseCase {

    UserDetailResponse createUserDetail(Long userId, UserDetailRequest userDetailRequest);

    @Transactional
    UserDetailResponse createTempUserDetail(Long userId, String nickname);
}
