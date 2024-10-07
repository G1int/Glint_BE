package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.service.UserService;
import com.swyp.glint.user.application.usecase.UserAuthUseCase;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthUseCaseImpl implements UserAuthUseCase {

    private final UserService userService;

    private final UserDetailService userDetailService;


    @Transactional
    @Override
    public UserLoginResponse oauthLoginUser(UserRequest userRequest) {
        User user = userRequest.toEntity();

        Optional<User> userOptional = userService.findBy(user.getEmail());

        if(userOptional.isEmpty()) {
            return UserLoginResponse.from(userService.save(userRequest.toEntity()), false);
        }

        Optional<UserDetail> userDetailOptional = userDetailService.getUserDetailOptional(userOptional.get().getId());

        // 이미 회원가입은 했지만 detail 없는경우
        if(userDetailOptional.isEmpty()) {
            return UserLoginResponse.from(userOptional.get(), false);
        }

        UserDetail userDetail = userDetailOptional.get();

        // 이미 회원가입은 했지만 detail 있지만 완료하지 않은경우
        if(userDetail.isNotComplete()) {
            return UserLoginResponse.from(userOptional.get(), false);
        }

        // 이미 회원가입 했고 detail까지 완료한경우
        return UserLoginResponse.from(userOptional.get(), true);
    }


}
