package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        User user = userRequest.toEntity();
        return UserResponse.from(userRepository.save(user));
    }


    public UserResponse getUserById(Long id) {
        return UserResponse.from(userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found")));

    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundEntityException("email : " + email + " not found"));
    }

    @Transactional
    public UserLoginResponse oauthLoginUser(UserRequest userRequest) {
        User user = userRequest.toEntity();

        User foundUser = userRepository.findByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(userRequest.toEntity()));

        return UserLoginResponse.from(foundUser);
    }


    public List<User> getUsers(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }


    public List<UserMeetingResponse> getUserMeetingResponseList(List<Long> userIds) {
        return userRepository.findByUserIds(userIds).stream().map(UserMeetingResponse::from).toList();
    }


}
