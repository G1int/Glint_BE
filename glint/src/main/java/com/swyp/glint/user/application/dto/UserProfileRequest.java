package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.domain.UserProfile;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileRequest(

        String workName,
        String universityName,
        String locationState,
        String locationCity,
        String religionName,
        String smokingName,
        String drinkingName,
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
                .workName(work.getWorkName())
                .universityName(university.getUniversityName())
                .locationState(location.getState())
                .locationCity(location.getCity())
                .religionName(religion.getReligionName())
                .smokingName(smoking.getSmokingName())
                .drinkingName(drinking.getDrinkingName())
                .selfIntroduction(selfIntroduction)
                .hashtags(hashtags)
                .build();
    }

}
