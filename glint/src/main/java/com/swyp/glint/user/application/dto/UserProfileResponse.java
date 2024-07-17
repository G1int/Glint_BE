package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileResponse(
        @Parameter(description = "UserProfile ID", example = "1", required = true)
        Long id,
        @Parameter(description = "User ID", example = "1", required = true)
        Long userId,
        @Parameter(description = "닉네임", example = "새싹이")
        String nickname,
        @Parameter(description = "나이", example = "28")
        Integer age,
        @Parameter(description = "Work ID", example = "1")
        Long workId,
        @Parameter(description = "Work Category ID", example = "1")
        Long workCategoryId,
        @Parameter(description = "직업명", example = "삼성전자")
        String workName,
        @Parameter(description = "University ID", example = "1")
        Long universityId,
        @Parameter(description = "University Category ID", example = "1")
        Long universityCategoryId,
        @Parameter(description = "대학명", example = "중앙대학교")
        String universityName,
        @Parameter(description = "대학 학과명", example = "의예과")
        String universityDepartment,
        @Parameter(description = "Location ID", example = "1")
        Long locationId,
        @Parameter(description = "위치의 [시,도]", example = "서울특별시")
        String locationState,
        @Parameter(description = "위치의 [시,군,구]", example = "강남구")
        String locationCity,
        @Parameter(description = "Religion ID", example = "1")
        Long religionId,
        @Parameter(description = "종교명", example = "기독교")
        String religionName,
        @Parameter(description = "Smoking ID", example = "1")
        Long smokingId,
        @Parameter(description = "흡연명", example = "흡연")
        String smokingName,
        @Parameter(description = "Drinking ID", example = "1")
        Long drinkingId,
        @Parameter(description = "음주명", example = "마시지 않음")
        String drinkingName,
        @Parameter(description = "자기소개", example = "안녕하세요 저는 서울 강북구에 사는 유재석이라고 합니다.")
        String selfIntroduction,
        @Parameter(description = "나를 표현하는 키워드", example = "[적극적], [ESTJ], [애교많음]")
        List<String> hashtags
) {
    public static UserProfileResponse from(UserProfile userProfile, UserDetail userDetail) {
        return UserProfileResponse.builder()
                .id(userProfile.getId())
                .userId(userProfile.getUserId())
                .nickname(userDetail.getNickname())
                .age(userDetail.calculateAgeByBirthdate())
                .workId(userProfile.getWork().getId())
                .workCategoryId(userProfile.getWork().getWorkCategoryId())
                .workName(userProfile.getWork().getWorkName())
                .universityId(userProfile.getUniversity().getId())
                .universityCategoryId(userProfile.getUniversity().getUniversityCategoryId())
                .universityName(userProfile.getUniversity().getUniversityName())
                .universityDepartment(userProfile.getUniversity().getUniversityDepartment())
                .locationId(userProfile.getLocation().getId())
                .locationState(userProfile.getLocation().getState())
                .locationCity(userProfile.getLocation().getCity())
                .religionId(userProfile.getReligion().getId())
                .religionName(userProfile.getReligion().getReligionName())
                .smokingId(userProfile.getSmoking().getId())
                .smokingName(userProfile.getSmoking().getSmokingName())
                .drinkingId(userProfile.getDrinking().getId())
                .drinkingName(userProfile.getDrinking().getDrinkingName())
                .selfIntroduction(userProfile.getSelfIntroduction())
                .hashtags(userProfile.getHashtags())
                .build();
    }
}
