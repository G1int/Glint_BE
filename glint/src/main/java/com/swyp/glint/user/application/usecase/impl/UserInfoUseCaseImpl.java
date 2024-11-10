package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.usecase.UserInfoUseCase;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserInfo;
import com.swyp.glint.user.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoUseCaseImpl implements UserInfoUseCase {

    private final UserDetailService userDetailService;
    private final UserProfileService userProfileService;


    @Override
    public UserInfoResponse getUserInfoBy(Long userId) {
        UserDetail userDetail = userDetailService.getUserDetailBy(userId);
        UserProfile userProfile = userProfileService.getUserProfileBy(userId);
        return UserInfoResponse.from(UserInfo.of(userDetail, userProfile));
    }



}
