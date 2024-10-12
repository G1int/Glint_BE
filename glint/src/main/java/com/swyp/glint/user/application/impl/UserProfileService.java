package com.swyp.glint.user.application.impl;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.*;
import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileResponse;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.infra.UserProfileRepository;
import jakarta.transaction.Transactional;
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

    public UserProfile createEmptyUserProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseGet(() -> userProfileRepository.save(UserProfile.createEmptyProfile(userId)));

    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public Optional<UserProfile> findBy(Long userId) {
        return userProfileRepository.findByUserId(userId);
    }
}
