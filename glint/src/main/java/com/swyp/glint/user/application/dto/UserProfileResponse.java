package com.swyp.glint.user.application.dto;

import com.swyp.glint.keyword.domain.UniversityCategory;
import com.swyp.glint.keyword.domain.WorkCategory;
import com.swyp.glint.user.domain.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    @Schema(description = "User Profile ID", example = "1", nullable = false)
    private Long id;
    private WorkResponse work;
    private UniversityResponse university;
    private LocationResponse location;
    private ReligionResponse religion;
    private SmokingResponse smoking;
    private DrinkingResponse drinking;
    @Schema(description = "자기소개", example = "안녕하세요 저는 서울 강북구에 사는 유재석이라고 합니다.")
    private  String selfIntroduction;
    @Schema(description = "나를 표현하는 키워드", example = "[적극적, ESTJ, 애교많음]")
    private List<String> hashtags;


    public static UserProfileResponse from(UserProfile userProfile, WorkCategory workCategory, UniversityCategory universityCategory) {
        if(Objects.isNull(userProfile)) return null;

        return UserProfileResponse.builder()
                .id(userProfile.getId())
                .work(WorkResponse.from(userProfile.getWork(), workCategory))
                .university(UniversityResponse.from(userProfile.getUniversity(), universityCategory))
                .location(LocationResponse.from(userProfile.getLocation()))
                .religion(ReligionResponse.from(userProfile.getReligion()))
                .smoking(SmokingResponse.from(userProfile.getSmoking()))
                .drinking(DrinkingResponse.from(userProfile.getDrinking()))
                .selfIntroduction(userProfile.getSelfIntroduction())
                .hashtags(userProfile.getHashtags())
                .build();
    }
}
