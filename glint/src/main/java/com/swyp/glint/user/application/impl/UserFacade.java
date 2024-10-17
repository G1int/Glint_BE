package com.swyp.glint.user.application.impl;

import com.swyp.glint.user.application.service.impl.UserServiceImpl;
import com.swyp.glint.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserDetailService userDetailService;

    private final UserProfileService userProfileService;

    public UserSimpleProfile getUserSimpleProfile(Long userId) {
        UserDetail userDetail = userDetailService.getUserDetailBy(userId);
        UserProfile userProfile = userProfileService.getUserProfileBy(userId);
        return UserSimpleProfile.of(userDetail, userProfile);
    }

}
