package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.domain.UserProfile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileWithDetailResponse {

    @Schema(description = "UserProfile ID", example = "1", nullable = false)
    Long id;
    @Schema(description = "User ID", example = "1", nullable = false)
    Long userId;
    UserDetailResponse userDetail;
    @Schema(description = "Work ID", example = "1")
    Long workId;
    @Schema(description = "Work Category ID", example = "1")
    Long workCategoryId;
    @Schema(description = "Work Category Name", example = "1")
    String workCategoryName;
    @Schema(description = "직업명", example = "삼성전자")
    String workName;
    @Schema(description = "University ID", example = "1")
    Long universityId;
    @Schema(description = "University Category ID", example = "1")
    Long universityCategoryId;
    @Schema(description = "University Category ID", example = "1")
    String universityCategoryName;
    @Schema(description = "대학명", example = "중앙대학교")
    String universityName;
    @Schema(description = "대학 학;명", example = "의예과")
    String universityDepartment;
    @Schema(description = "Location ID", example = "1")
    Long locationId;
    @Schema(description = "위치의 [시,도]", example = "서울특별시")
    String locationState;
    @Schema(description = "위치의 [시,군,구]", example = "강남구")
    String locationCity;
    @Schema(description = "Religion ID", example = "1")
    Long religionId;
    @Schema(description = "종교명", example = "기독교")
    String religionName;
    @Schema(description = "Smoking ID", example = "1")
    Long smokingId;
    @Schema(description = "흡연명", example = "흡연")
    String smokingName;
    @Schema(description = "Drinking ID", example = "1")
    Long drinkingId;
    @Schema(description = "음주명", example = "마시지 않음")
    String drinkingName;
    @Schema(description = "자기소개", example = "안녕하세요 저는 서울 강북구에 사는 유재석이라고 합니다.")
    String selfIntroduction;
    @Schema(description = "나를 표현하는 키워드", example = "[적극적, ESTJ, 애교많음]")
    List<String> hashtags;
    

    public UserProfileWithDetailResponse(UserProfile userProfile, UserDetail userDetail) {
                this.id=userProfile.getId();
                this.userId = userDetail.getUserId();
                this.userDetail = UserDetailResponse.from(userDetail);


                this.workId=userProfile.getWork().getId();
                this.workName=userProfile.getWork().getWorkName();

                this.workCategoryId=userProfile.getWork().getWorkCategoryId();

                this.universityId=userProfile.getUniversity().getId();
                this.universityName=userProfile.getUniversity().getUniversityName();
                this.universityDepartment=userProfile.getUniversity().getUniversityDepartment();

                this.universityCategoryId=userProfile.getUniversity().getUniversityCategoryId();

                this.locationId=userProfile.getLocation().getId();
                this.locationState=userProfile.getLocation().getState();
                this.locationCity=userProfile.getLocation().getCity();


                this.religionId=userProfile.getReligion().getId();
                this.religionName=userProfile.getReligion().getReligionName();

                this.smokingId=userProfile.getSmoking().getId();
                this.smokingName=userProfile.getSmoking().getSmokingName();

                this.drinkingId=userProfile.getDrinking().getId();
                this.drinkingName=userProfile.getDrinking().getDrinkingName();

                this.selfIntroduction=userProfile.getSelfIntroduction();
                this.hashtags=userProfile.getHashtags();
    }


        public static UserProfileWithDetailResponse from(UserProfile userProfile, UserDetail userDetail) {
        return UserProfileWithDetailResponse.builder()
                .id(userProfile.getId())
                .userId(userDetail.getUserId())
                .userDetail(UserDetailResponse.from(userDetail))
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
