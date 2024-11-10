package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.meeting.application.usecase.impl.UserMeetingUseCaseImpl;
import com.swyp.glint.user.application.service.UserService;
import com.swyp.glint.user.application.usecase.DeleteUserMeetingUseCase;
import com.swyp.glint.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUserMeetingUseCaseImpl implements DeleteUserMeetingUseCase {

    private final UserMeetingUseCaseImpl userMeetingUseCase;
    private final UserService userService;


    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = userService.getUserEntity(userId);
        user.archive();
        userMeetingUseCase.deleteUserMeeting(userId);
    }
}
