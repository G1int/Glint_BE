package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Builder
public record UserDetailResponse(
        @Schema(description = "UserDetail ID", example = "1", required = true)
        Long id,
        @Schema(description = "User ID", example = "1", required = true)
        Long userId,
        @Schema(description = "User Nickname", example = "nickname", required = false)
        String nickname,
        @Schema(description = "User Gender", example = "gender", required = false)
        String gender,
        @Schema(description = "User Birthdate", example = "2000-01-01", required = false)
        String birthdate,
        @Schema(description = "User Height", example = "180", required = false)
        Integer height,
        @Schema(description = "User Profile Image", example = "https://glint-image.s3.ap-northeast-2.amazonaws.com/profile/profile_1720106931.png", required = false)
        String profileImage
) {
    public static UserDetailResponse from(UserDetail userDetail) {
        if(userDetail == null) return null;
        return UserDetailResponse.builder()
                .id(userDetail.getId())
                .userId(userDetail.getUserId())
                .nickname(userDetail.getNickname())
                .gender(userDetail.getGender())
                .birthdate(Optional.ofNullable(userDetail.getBirthdate()).map(date -> date.format(DateTimeFormatter.ISO_DATE)).orElse(null))
                .height(userDetail.getHeight())
                .profileImage(userDetail.getProfileImage())
                .build();
    }
}
