package com.swyp.glint.user.application.impl;

import com.swyp.glint.meeting.application.MeetingFacade;
import com.swyp.glint.user.application.service.impl.UserServiceImpl;
import com.swyp.glint.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserMeetingFacade {

    private final MeetingFacade meetingFacade;

    private final UserServiceImpl userService;


    @Transactional
    public void deleteUser(Long userId) {
        User user = userService.getUserEntity(userId);
        user.archive();
//        userService.deleteUser();
        meetingFacade.deleteUserMeeting(userId);
            // 참가중인 미팅 모두 나가기
    }
}