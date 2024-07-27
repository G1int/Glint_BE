package com.swyp.glint.user.application;

import com.swyp.glint.common.exception.InvalidValueException;
import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.application.*;
import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.dto.UserProfileResponse;
import com.swyp.glint.user.application.dto.UserResponse;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import com.swyp.glint.user.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private final WorkCategoryService workCategoryService;

    @Transactional
    public UserInfoResponse createUserProfile(Long userId, UserProfileRequest userProfileRequest) {
        Optional<UserProfile> existingUserProfile = userProfileRepository.findByUserId(userId);
        if (existingUserProfile.isPresent()) {
            throw new InvalidValueException("User Profile with userId: " + userId + " already exists");
        }

        Work work = workService.createNewWork(userProfileRequest.workName());
        University university = universityService.getEntityByName(userProfileRequest.universityName(), userProfileRequest.universityDepartment());
        Location location = locationService.getEntityByName(userProfileRequest.locationState(), userProfileRequest.locationCity());
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

        userProfileRepository.save(userProfile);

        // todo response 수정
        //  밑에 getUserInfo를 호출하지 않고 여기서 조합해야함.
        return userProfileRepository.findUserInfoBy(userId).orElseThrow(() -> new NotFoundEntityException("id : " + userId + " not found"));
    }


    public UserProfile getUserProfileEntityById(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundEntityException("User Profile with userId: " + userId + " not found"));
    }

    public UserProfileResponse getUserProfileById(Long userId) {
        UserProfile userProfile = getUserProfileEntityById(userId);
        UniversityCategory universityCategory = universityService.getUniversityCategoryByUniversity(userProfile.getUniversity())
                .orElse(null);
        WorkCategory workCategory = workCategoryService.getUWorkCategoryByWork(userProfile.getWork())
                .orElse(null);
        return UserProfileResponse.from(userProfile, workCategory, universityCategory);
    }

    @Transactional
    public UserInfoResponse updateUserProfile(Long userId, UserProfileRequest userProfileRequest) {
        Work work = Optional.ofNullable(userProfileRequest.workName()).map(workService::createNewWork).orElse(null);

        University university = getUniversityOrElseNull(userProfileRequest);
        Location location = getLocationOrElseNull(userProfileRequest);
        Religion religion = getReligionOrElseNull(userProfileRequest);
        Smoking smoking = getSmokingOrElseNull(userProfileRequest);
        Drinking drinking = getDrinkingOrElseNull(userProfileRequest);

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

        // todo response 수정
        //  밑에 getUserInfo를 호출하지 않고 여기서 조합해야함.
        return userProfileRepository.findUserInfoBy(userId).orElseThrow(() -> new NotFoundEntityException("id : " + userId + " not found"));
    }

    private Drinking getDrinkingOrElseNull(UserProfileRequest userProfileRequest) {
        return Optional.ofNullable(userProfileRequest.drinkingId())
                .map(drinkingService::findById)
                .orElse(null);
    }

    private Smoking getSmokingOrElseNull(UserProfileRequest userProfileRequest) {
        return Optional.ofNullable(userProfileRequest.smokingId())
                .map(smokingService::findById)
                .orElse(null);
    }

    private Religion getReligionOrElseNull(UserProfileRequest userProfileRequest) {
        return Optional.ofNullable(userProfileRequest.religionId())
                .map(religionService::findById)
                .orElse(null);
    }

    private Location getLocationOrElseNull(UserProfileRequest userProfileRequest) {
        return Optional.ofNullable(userProfileRequest.locationState())
                .filter(locationState -> !locationState.isEmpty())
                .map(locationState -> locationService.getEntityByName(locationState, userProfileRequest.locationCity()))
                .orElse(null);
    }

    private University getUniversityOrElseNull(UserProfileRequest userProfileRequest) {
        return Optional.ofNullable(userProfileRequest.universityName())
                .filter(universityName -> !universityName.isEmpty())
                .map(universityName -> universityService.getEntityByName(universityName, userProfileRequest.universityDepartment()))
                .orElse(null);
    }

}
