package com.swyp.glint.user.application.impl;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.user.application.UserService;
import com.swyp.glint.user.application.dto.*;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserSimpleProfile;
import com.swyp.glint.user.infra.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserResponse getUserById(Long id) {
        return UserResponse.from(userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found")));

    }

    @Override
    public UserInfoResponse getUserInfoBy(Long id) {
        return userRepository.findUserInfoBy(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    @Override
    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundEntityException("email : " + email + " not found"));
    }


    public List<User> getUsers(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }


    public List<UserMeetingResponse> getUserMeetingResponseList(List<Long> userIds) {
        return userRepository.findByUserIds(userIds).stream().map(UserMeetingResponse::from).toList();
    }

    @Override
    public List<UserSimpleProfile> getUserSimpleProfileList(List<Long> userIds) {
        return userRepository.findByUserIds(userIds);
    }


    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findBy(String email) {
        return userRepository.findByEmail(email);
    }
}
