package com.swyp.glint.user.application;

import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import jakarta.transaction.Transactional;

public interface UserAuthService {
    @Transactional
    UserLoginResponse oauthLoginUser(UserRequest userRequest);
}
