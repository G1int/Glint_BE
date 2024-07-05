package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import lombok.Builder;

@Builder
public record UserDetailResponse(
        Long id,
        String nickname,
        String gender,
        String birthdate,
        Integer height,
        String profileImage
) {
    public static UserDetailResponse from(UserDetail userDetail) {
        return UserDetailResponse.builder()
                .id(userDetail.getId())
                .nickname(userDetail.getNickname())
                .gender(userDetail.getGender())
                .birthdate(userDetail.getBirthdate())
                .height(userDetail.getHeight())
                .profileImage(userDetail.getProfileImage())
                .build();
    }
}
