package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.usecase.UserNickNameUseCase;
import com.swyp.glint.user.domain.NickNameValidator;
import com.swyp.glint.user.domain.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserNickNameUseCaseImpl implements UserNickNameUseCase {

    private final UserDetailService userDetailService;

    @Override
    public UserNickNameValidationResponse isNicknameTaken(String nickname) {
        if (!NickNameValidator.isValid(nickname)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_INVALID);
        }

        Optional<UserDetail> userDetailOptional = userDetailService.findBy(nickname);

        if(userDetailOptional.isPresent()) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATED);
        }

        return UserNickNameValidationResponse.from(true, nickname);
    }
}
