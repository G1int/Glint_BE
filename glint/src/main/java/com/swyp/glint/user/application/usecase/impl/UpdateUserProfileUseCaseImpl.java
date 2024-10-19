package com.swyp.glint.user.application.usecase.impl;

import com.swyp.glint.keyword.application.*;
import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.application.dto.UserInfoResponse;
import com.swyp.glint.user.application.dto.UserProfileRequest;
import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.application.impl.UserProfileService;
import com.swyp.glint.user.application.usecase.UpdateUserProfileUseCase;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserInfo;
import com.swyp.glint.user.domain.UserProfile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateUserProfileUseCaseImpl implements UpdateUserProfileUseCase {

    private final UserProfileService userProfileService;
    private final UserDetailService userDetailService;
    private final DrinkingService drinkingService;
    private final LocationService locationService;
    private final ReligionService religionService;
    private final SmokingService smokingService;
    private final UniversityService universityService;
    private final WorkService workService;

    @Override
    @Transactional
    public UserInfoResponse updateUserProfile(Long userId, UserProfileRequest userProfileRequest) {
        Work work = Optional.ofNullable(userProfileRequest.workName()).map(workService::createNewWork).orElse(null);
        University university = getUniversityOrElseNull(userProfileRequest.universityName(), userProfileRequest.universityDepartment());
        Location location = getLocationOrElseNull(userProfileRequest.locationState(), userProfileRequest.locationCity());
        Religion religion = getReligionOrElseNull(userProfileRequest.religionId());
        Smoking smoking = getSmokingOrElseNull(userProfileRequest.smokingId());
        Drinking drinking = getDrinkingOrElseNull(userProfileRequest.drinkingId());


        UserProfile userProfile = userProfileService.findBy(userId)
                .orElseGet(() -> UserProfile.createNewUserProfile(
                        userId,
                        work,
                        university,
                        location,
                        religion,
                        smoking,
                        drinking,
                        userProfileRequest.selfIntroduction(),
                        userProfileRequest.hashtags()
                ));

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

        UserDetail userDetail = userDetailService.getUserDetailBy(userId);
        userProfileService.save(userProfile);

        return UserInfoResponse.from(UserInfo.of(userDetail, userProfile));

    }


    private Drinking getDrinkingOrElseNull(Long drinkingId) {
        return Optional.ofNullable(drinkingId)
                .map(drinkingService::getDrinkBy)
                .orElse(null);
    }

    private Smoking getSmokingOrElseNull(Long smokingId) {
        return Optional.ofNullable(smokingId)
                .map(smokingService::getSmokingById)
                .orElse(null);
    }

    private Religion getReligionOrElseNull(Long religionId) {
        return Optional.ofNullable(religionId)
                .map(religionService::getById)
                .orElse(null);
    }

    private Location getLocationOrElseNull(String locationState, String locationCity) {
        return Optional.ofNullable(locationState)
                .filter(ls -> !ls.isEmpty())
                .map(ls -> locationService.getEntityByName(locationState, locationCity))
                .orElse(null);
    }

    private University getUniversityOrElseNull(String universityName, String universityDepartment) {
        return Optional.ofNullable(universityName)
                .filter(name -> !name.isEmpty())
                .map(name -> universityService.getEntityByName(name, universityDepartment))
                .orElse(null);
    }

}