package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;

public interface UserNickNameUseCase {
    UserNickNameValidationResponse isNicknameTaken(String nickname);

    UserNickNameValidationResponse validateNickname(Long userId, String nickname);
}
