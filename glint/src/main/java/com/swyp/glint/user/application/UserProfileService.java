package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.*;
import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileWithDetailResponse;
import com.swyp.glint.user.application.dto.UserResponse;
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
    public UserInfoResponse createUserProfile(Long userId, UserProfileRequest userProfileRequest) {

        Work work = workService.createNewWork(userProfileRequest.workName());
        University university = universityService.findByName(userProfileRequest.universityName(), userProfileRequest.universityDepartment());
        Location location = locationService.findByName(userProfileRequest.locationState(), userProfileRequest.locationCity());
        Religion religion = religionService.findById(userProfileRequest.religionId());
        Smoking smoking = smokingService.findById(userProfileRequest.smokingId());
        Drinking drinking = drinkingService.findById(userProfileRequest.drinkingId());

        UserProfile userProfile = UserProfile.createNewUserProfile(
                userId,
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
        userDetail.updateProfileUrl(userProfileRequest.profileImageUrl());
        userProfileRepository.save(userProfile);

        // todo response 수정
        //  밑에 getUserInfo를 호출하지 않고 여기서 조합해야함.
        return userService.getUserInfoBy(userId);
    }

    public UserProfileWithDetailResponse getUserProfileById(Long userId) {
        return userProfileRepository.findUserInfoBy(userId)
                .orElseThrow(() -> new NotFoundEntityException("User Profile with userId: " + userId + " not found"));
    }

    public UserProfile getUserProfileEntityById(Long userId) {
        UserResponse userResponse = userService.getUserById(userId);
        return userProfileRepository.findByUserId(userResponse.id())
                .orElseThrow(() -> new NotFoundEntityException("User Profile with userId: " + userId + " not found"));
    }

    public List<UserProfileWithDetailResponse> getAllUserProfile() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream()
                .map(userProfile -> {
                    UserDetail userDetail = userDetailService.getUserDetail(userProfile.getUserId());
                    return UserProfileWithDetailResponse.from(userProfile, userDetail);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public UserInfoResponse updateUserProfile(Long userId, UserProfileRequest userProfileRequest) {
        UserResponse userResponse = userService.getUserById(userId);

        Work work = workService.createNewWork(userProfileRequest.workName());
        University university = universityService.findByName(userProfileRequest.universityName(), userProfileRequest.universityDepartment());
        Location location = locationService.findByName(userProfileRequest.locationState(), userProfileRequest.locationCity());
        Religion religion = religionService.findById(userProfileRequest.religionId());
        Smoking smoking = smokingService.findById(userProfileRequest.smokingId());
        Drinking drinking = drinkingService.findById(userProfileRequest.drinkingId());

        UserProfile userProfile = userProfileRepository.findByUserId(userId).orElseGet(() -> {
            return UserProfile.createNewUserProfile(
                    userId,
                    work,
                    university,
                    location,
                    religion,
                    smoking,
                    drinking,
                    userProfileRequest.selfIntroduction(),
                    userProfileRequest.hashtags()
            );
        });


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
        userProfileRepository.save(userProfile);
        userDetailService.updateUserProfileImage(userId, userProfileRequest.profileImageUrl());

        // todo response 수정
        //  밑에 getUserInfo를 호출하지 않고 여기서 조합해야함.
        return userService.getUserInfoBy(userId);
    }
}
