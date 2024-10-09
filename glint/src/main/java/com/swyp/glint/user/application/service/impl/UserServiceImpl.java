package com.swyp.glint.user.application.service.impl;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.user.application.service.UserService;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserInfo;
import com.swyp.glint.user.domain.UserSimpleProfile;
import com.swyp.glint.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserBy(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    @Override
    public User getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));

    }

    @Override
    public User getUserBy(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundEntityException("email : " + email + " not found"));
    }


    public List<User> getUsers(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findBy(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserInfo getUserInfoBy(Long id) {
        return userRepository.findUserInfoBy(id).orElseThrow(() -> new NotFoundEntityException("id : " + id + " not found"));
    }


}
