package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.image.application.ImageService;
import com.swyp.glint.image.application.dto.ImageResponse;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.usecase.UpdateUserDetailUseCase;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UpdateUserDetailUseCaseImpl implements UpdateUserDetailUseCase {

    private final UserDetailService userDetailService;

    private final ImageService imageService;

    @Transactional
    @Override
    public UserDetailResponse updateUserDetail(Long userId, UserDetailRequest userDetailRequest) {

        UserDetail userDetail = userDetailService.findBy(userId)
                .orElse(userDetailRequest.toEntity(userId));

        userDetail.updateUserDetail(
                userDetailRequest.nickname(),
                userDetailRequest.gender(),
                LocalDate.parse(userDetailRequest.birthdate()),
                Integer.parseInt(userDetailRequest.height()),
                userDetailRequest.profileImage()
        );

        return UserDetailResponse.from(userDetailService.save(userDetail));
    }


    @Transactional
    @Override
    public UserDetailResponse updateUserProfileImage(Long userId, MultipartFile userProfileImageFile) {
        UserDetail userDetail = userDetailService.getUserDetailBy(userId);
        ImageResponse imageResponse = imageService.uploadProfileImageFile(userProfileImageFile);
        userDetail.updateProfileUrl(imageResponse.url());
        return UserDetailResponse.from(userDetailService.save(userDetail));
    }

}
