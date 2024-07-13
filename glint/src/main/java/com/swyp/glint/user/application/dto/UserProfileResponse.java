package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserProfile;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileResponse(
        Long id,
        Long userId,
        Long workId,
        String workName,
        Long universityId,
        String universityName,
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
    public static UserProfileResponse from(UserProfile userProfile) {
        return UserProfileResponse.builder()
                .id(userProfile.getId())
                .userId(userProfile.getUserId())
                .workId(userProfile.getWork().getId())
                .workName(userProfile.getWork().getWorkName())
                .universityId(userProfile.getUniversity().getId())
                .universityName(userProfile.getUniversity().getUniversityName())
                .locationState(userProfile.getLocation().getState())
                .locationCity(userProfile.getLocation().getCity())
                .religionId(userProfile.getReligion().getId())
                .religionState(userProfile.getReligion().getReligionName())
                .smokingId(userProfile.getSmoking().getId())
                .smokingState(userProfile.getSmoking().getSmokingName())
                .drinkingId(userProfile.getDrinking().getId())
                .drinkingState(userProfile.getDrinking().getDrinkingName())
                .selfIntroduction(userProfile.getSelfIntroduction())
                .hashtags(userProfile.getHashtags())
                .build();
    }
}
