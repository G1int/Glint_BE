package com.swyp.glint.user.application.impl;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile getUserProfileBy(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("User Profile with userId: " + userId + " not found"));
    }

    public List<UserProfile> getUserProfileByIds(List<Long> userIds) {
        return userProfileRepository.findAllByUserId(userIds);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public Optional<UserProfile> findBy(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }
}
