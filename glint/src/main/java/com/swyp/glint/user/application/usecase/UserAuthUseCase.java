package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import jakarta.transaction.Transactional;

public interface UserAuthUseCase {

    UserLoginResponse oauthLoginUser(UserRequest userRequest);
}
