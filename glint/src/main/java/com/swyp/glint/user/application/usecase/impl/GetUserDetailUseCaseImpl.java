package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.usecase.GetUserDetailUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserDetailUseCaseImpl implements GetUserDetailUseCase {

    private final UserDetailService userDetailService;

    @Override
    public UserDetailResponse getUserDetailBy(Long userId) {
        return UserDetailResponse.from(userDetailService.getUserDetailBy(userId));
    }



}
