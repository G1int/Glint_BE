package com.swyp.glint.user.application.usecase;

import org.springframework.transaction.annotation.Transactional;

public interface DeleteUserMeetingUseCase {
    @Transactional
    void deleteUser(Long userId);
}
