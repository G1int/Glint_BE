package com.swyp.glint.user.service.impl;

import com.swyp.glint.user.api.dto.UserRequest;
import com.swyp.glint.user.api.dto.UserResponse;
import com.swyp.glint.user.service.UserService;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userRequest.toEntity();
        return UserResponse.from(userRepository.save(user));
    }

    @Override
    public UserResponse getUser(Long id) {

        return UserResponse.from(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저가 존재하지 않습니다.")));

    }
}
