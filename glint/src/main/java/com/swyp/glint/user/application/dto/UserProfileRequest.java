package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.*;
import com.swyp.glint.user.domain.UserProfile;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileRequest(

        @Parameter(description = "직장명", example = "삼성전자")
        String workName,

        @Parameter(description = "대학명", example = "중앙대학교")
        String universityName,

        @Parameter(description = "대학 학과명", example = "의예과")
        String universityDepartment,

        @Parameter(description = "위치의 [시,도]", example = "서울특별시")
        String locationState,

        @Parameter(description = "위치의 [시, 군, 구]", example = "강남구")
        String locationCity,

        @Parameter(description = "종교명", example = "무교")
        String religionName,

        @Parameter(description = "흡연명", example = "비흡연")
        String smokingName,

        @Parameter(description = "음주명", example = "가끔마심")
        String drinkingName,

        @Parameter(description = "자기소개", example = "안녕하세요! 저는 강아지를 좋아하고 활기찹니다.")
        @Size(max = 300, message = "자기소개는 최대 300자까지 가능합니다.")
        String selfIntroduction,

        @Parameter(description = "나를 표현하는 키워드", example = "[적극적], [ESTJ], [애교많음]")
        @Size(max = 10, message = "최대 10개의 키워드만 허용됩니다.")
        List<@Size(min = 1, max = 15, message = "각 키워드는 1글자에서 15글자 사이여야 합니다.") String> hashtags

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
                .universityDepartment(university.getUniversityDepartment())
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
