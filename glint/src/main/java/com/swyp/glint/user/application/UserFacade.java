package com.swyp.glint.user.application;

import com.swyp.glint.chatting.domain.UserChat;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserDetailAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    private final UserDetailService userDetailService;


    public UserDetailAggregation getUserDetailAggregation(Long userId) {
        User user = userService.getUserEntity(userId);
        UserDetail userDetail = userDetailService.getUserDetail(userId);

        return UserDetailAggregation.of(user, userDetail);
    }

}
