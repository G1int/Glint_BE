package com.swyp.glint.user.application;

import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import com.swyp.glint.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    private final UserDetailService userDetailService;

    private final UserProfileService userProfileService;


    public UserDetailAggregation getUserDetailAggregation(Long userId) {
        User user = userService.getUserEntity(userId);
        UserDetail userDetail = userDetailService.getUserDetail(userId);

        return UserDetailAggregation.of(user, userDetail);
    }

    public UserSimpleProfile getUserSimpleProfile(Long userId) {
        UserDetail userDetail = userDetailService.getUserDetail(userId);
        UserProfile userProfile = userProfileService.getUserProfileEntityById(userId);
        return UserSimpleProfile.of(userDetail, userProfile);
    }

}
