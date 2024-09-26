package com.swyp.glint.user.application.impl;

import com.swyp.glint.user.application.UserAuthService;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserDetailService userDetailService;

    private final UserService userService;

    @Transactional
    @Override
    public UserLoginResponse oauthLoginUser(UserRequest userRequest) {
        User user = userRequest.toEntity();

        Optional<User> userOptional = userService.findBy(user.getEmail());

        if(userOptional.isEmpty()) {
            return UserLoginResponse.from(userService.save(userRequest.toEntity()), false);
        }

        Optional<UserDetail> userDetailOptional = userDetailService.findUserDetail(userOptional.get().getId());

        // 이미 회원가입은 했지만 detail 없는경우
        if(userDetailOptional.isEmpty()) {
            return UserLoginResponse.from(userOptional.get(), false);
        }
        // 이미 회원가입은 했지만 detail 있지만 완료하지 않은경우
        if(! userDetailOptional.get().isComplete()) {
            return UserLoginResponse.from(userOptional.get(), false);
        }

        // 이미 회원가입 했고 detail까지 완료한경우
        return UserLoginResponse.from(userOptional.get(), true);
    }


}
