package com.swyp.glint.user.application.service.impl;

import com.swyp.glint.user.application.service.UserSimpleProfileService;
import com.swyp.glint.user.domain.UserSimpleProfile;
import com.swyp.glint.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSimpleProfileServiceImpl implements UserSimpleProfileService {

    private final UserRepository userRepository;

    @Override
    public List<UserSimpleProfile> getUserSimpleProfileList(List<Long> userIds) {
        return userRepository.findByUserIds(userIds);
    }

}
