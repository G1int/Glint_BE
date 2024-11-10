package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.core.common.cache.CacheStore;
import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.usecase.UserNickNameUseCase;
import com.swyp.glint.user.domain.NickNameValidator;
import com.swyp.glint.user.domain.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserNickNameUseCaseImpl implements UserNickNameUseCase {

    private final UserDetailService userDetailService;

    private final CacheStore cacheStore;

    private final Integer NICKNAME_EXPIRE_MINUTES = 20;

    @Override
    public UserNickNameValidationResponse isNicknameTaken(String nickname) {
        if (NickNameValidator.isInvalid(nickname)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_INVALID);
        }

        Optional<UserDetail> userDetailOptional = userDetailService.findBy(nickname);

        if(userDetailOptional.isPresent()) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATED);
        }

        return UserNickNameValidationResponse.from(true, nickname);
    }


    @Override
    public UserNickNameValidationResponse validateNickname(Long userId, String nickname) {

        String validateNickNameUserId = cacheStore.getData(nickname);

        if(Objects.nonNull(validateNickNameUserId) && isNotEqual(userId, validateNickNameUserId)) {
            throw new InvalidValueException(ErrorCode.NICKNAME_DUPLICATED);
        }

        cacheStore.setDataExpire(nickname, userId.toString(), Duration.ofMinutes(NICKNAME_EXPIRE_MINUTES));

        return UserNickNameValidationResponse.from(Boolean.TRUE, nickname);
    }

    private boolean isNotEqual(Long userId, String validateNickNameUserId) {
        return !validateNickNameUserId.equals(userId.toString());
    }


}
