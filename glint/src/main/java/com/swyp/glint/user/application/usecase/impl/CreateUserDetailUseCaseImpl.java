package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.usecase.CreateUserDetailUseCase;
import com.swyp.glint.user.domain.NickNameValidator;
import com.swyp.glint.user.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateUserDetailUseCaseImpl implements CreateUserDetailUseCase {

    private final UserDetailService userDetailService;

    private final UserProfileService userProfileService;

    @Override
    @Transactional
    public UserDetailResponse createUserDetail(Long userId, UserDetailRequest userDetailRequest) {
        UserDetail userDetail = userDetailRequest.toEntity(userId);
        userProfileService.createEmptyUserProfile(userId);
        return UserDetailResponse.from(userDetailService.save(userDetail));
    }


    @Transactional
    @Override
    public UserDetailResponse createTempUserDetail(Long userId, String nickname) {
        if (!NickNameValidator.isValid(nickname)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_INVALID);
        }
        Optional<UserDetail> userDetailOptional = userDetailService.findBy(nickname);

        if(userDetailOptional.isPresent() && ! userDetailOptional.get().getUserId().equals(userId)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATED);
        }

        UserDetail userDetail = userDetailService.findBy(userId)
                .orElseGet(() -> userDetailService.save(UserDetail.createTempUserDetailByNickName(userId)));

        userDetail.updateNickname(nickname);

        return UserDetailResponse.from(userDetail);
    }


}
