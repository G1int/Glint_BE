package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.*;
import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileResponse;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final UserService userService;
    private final UserDetailService userDetailService;
    private final DrinkingService drinkingService;
    private final LocationService locationService;
    private final ReligionService religionService;
    private final SmokingService smokingService;
    private final UniversityService universityService;
    private final WorkService workService;

    @Transactional
    public UserProfileResponse createUserProfile(Long userId, UserProfileRequest userProfileRequest) {

        Work work = workService.createNewWork(userProfileRequest.workName());
        University university = universityService.findByName(userProfileRequest.universityName(), userProfileRequest.universityDepartment());
        Location location = locationService.findByName(userProfileRequest.locationState(), userProfileRequest.locationCity());
        Religion religion = religionService.findByName(userProfileRequest.religionName());
        Smoking smoking = smokingService.findByName(userProfileRequest.smokingName());
        Drinking drinking = drinkingService.findByName(userProfileRequest.drinkingName());

        UserProfile userProfile = userProfileRequest.toEntity(userId, work, university, location, religion, smoking, drinking);
        UserDetail userDetail = userDetailService.getUserDetail(userId);
        return UserProfileResponse.from(userProfileRepository.save(userProfile), userDetail);
    }

    public UserProfileResponse getUserProfileById(Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        UserDetail userDetail = userDetailService.getUserDetail(userId);
        return UserProfileResponse.from(userProfileRepository.findByUserId(userResponse.id())
                .orElseThrow(() -> new NotFoundEntityException("User Profile with userId: " + userId + " not found")), userDetail);
    }

    public UserProfile getUserProfileEntityById(Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return userProfileRepository.findByUserId(userResponse.id())
                .orElseThrow(() -> new NotFoundEntityException("User Profile with userId: " + userId + " not found"));
    }

    public List<UserProfileResponse> getAllUserProfile() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream()
                .map(userProfile -> {
                    UserDetail userDetail = userDetailService.getUserDetail(userProfile.getUserId());
                    return UserProfileResponse.from(userProfile, userDetail);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public UserProfileResponse updateUserProfile(Long userId, UserProfileRequest userProfileRequest) {
        UserResponse userResponse = userService.getUserById(userId);
        userDetailService.updateUserProfileImage(userId, userProfileRequest.profileImageUrl());

        Work work = workService.createNewWork(userProfileRequest.workName());
        University university = universityService.findByName(userProfileRequest.universityName(), userProfileRequest.universityDepartment());
        Location location = locationService.findByName(userProfileRequest.locationState(), userProfileRequest.locationCity());
        Religion religion = religionService.findByName(userProfileRequest.religionName());
        Smoking smoking = smokingService.findByName(userProfileRequest.smokingName());
        Drinking drinking = drinkingService.findByName(userProfileRequest.drinkingName());

        UserProfile userProfile = userProfileRepository.findByUserId(userResponse.id())
                .orElse(userProfileRequest.toEntity(userId, work, university, location, religion, smoking, drinking));

        userProfile.updateUserProfile(
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                userProfileRequest.selfIntroduction(),
                userProfileRequest.hashtags()
        );

        UserDetail userDetail = userDetailService.getUserDetail(userId);

        return UserProfileResponse.from(userProfileRepository.save(userProfile), userDetail);
    }
}
