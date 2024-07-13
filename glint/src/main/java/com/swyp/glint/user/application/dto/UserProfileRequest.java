package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.domain.UserProfile;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileRequest(

        Long workId,
        String workName,
        Long universityId,
        String universityName,
        Long locationId,
        String locationState,
        String locationCity,
        Long religionId,
        String religionState,
        Long smokingId,
        String smokingState,
        Long drinkingId,
        String drinkingState,
        String selfIntroduction,
        List<String> hashtags

) {
    public UserProfile toEntity(Long userId, Work work, University university, Location location,
                                Religion religion, Smoking smoking, Drinking drinking ) {

        return UserProfile.createNewUserProfile(
                userId,
                work,
                university,
                location,
                religion,
                smoking,
                drinking,
                selfIntroduction,
                hashtags
        );
    }

    public static UserProfileRequest of(Work work, University university, Location location, Religion religion, Smoking smoking,
                                        Drinking drinking, String selfIntroduction, List<String> hashtags) {
        return UserProfileRequest.builder()
                .workId(work.getId())
                .workName(work.getWorkName())
                .universityId(university.getId())
                .universityName(university.getUniversityName())
                .locationState(location.getState())
                .locationCity(location.getCity())
                .religionId(religion.getId())
                .religionState(religion.getReligionName())
                .smokingId(smoking.getId())
                .smokingState(smoking.getSmokingName())
                .drinkingId(drinking.getId())
                .drinkingState(drinking.getDrinkingName())
                .selfIntroduction(selfIntroduction)
                .hashtags(hashtags)
                .build();
    }

}
