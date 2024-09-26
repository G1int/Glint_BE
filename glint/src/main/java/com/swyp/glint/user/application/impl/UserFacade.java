package com.swyp.glint.user.application.impl;

import com.swyp.glint.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImpl userServiceImpl;

    private final UserDetailService userDetailService;

    private final UserProfileService userProfileService;


    public UserDetailAggregation getUserDetailAggregation(Long userId) {
        User user = userServiceImpl.getUserEntity(userId);
        UserDetail userDetail = userDetailService.getUserDetail(userId);

        return UserDetailAggregation.of(user, userDetail);
    }

    public UserSimpleProfile getUserSimpleProfile(Long userId) {
        UserDetail userDetail = userDetailService.getUserDetail(userId);
        UserProfile userProfile = userProfileService.getUserProfileEntityById(userId);
        return UserSimpleProfile.of(userDetail, userProfile);
    }

}
