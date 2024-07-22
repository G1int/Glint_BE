package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserDetailRequest(
        @Parameter(description = "닉네임", example = "철수", required = true)
        String nickname,

        @Parameter(description = "성별", example = "MALE|FEMALE", required = true)
        @Pattern(regexp = "(MALE|FEMALE)")
        String gender,

        @Parameter(description = "생년월일", example = "2000-01-01", required = true)
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
        String birthdate,

        @Parameter(description = "키", example = "180", required = true)
        @Pattern(regexp = "^[0-9]{3}$")
        String height,

        @Parameter(description = "프로필 이미지", example = "https://glint-image.s3.ap-northeast-2.amazonaws.com/profile/profile_1720106931.png", required = true)
        String profileImage
) {
    public UserDetail toEntity(Long userId) {
        return UserDetail.createNewUserDetail(
                userId,
                nickname,
                gender,
                LocalDate.parse(birthdate),
                Integer.parseInt(height),
                profileImage
        );
    }

    public static UserDetailRequest of(String nickname, String gender, String birthdate, Integer height, String profileImage) {
        return UserDetailRequest.builder()
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height.toString())
                .profileImage(profileImage)
                .build();
    }
}
