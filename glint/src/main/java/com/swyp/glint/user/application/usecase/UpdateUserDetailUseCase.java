package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateUserDetailUseCase {
    @Transactional
    UserDetailResponse updateUserDetail(Long userId, UserDetailRequest userDetailRequest);

    @Transactional
    UserDetailResponse updateUserProfileImage(Long userId, MultipartFile userProfileImageFile);
}
