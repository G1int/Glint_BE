package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import lombok.Builder;

@Builder
public record UserDetailRequest(
        Long userId,
        String nickname,
        String gender,
        String birthdate,
        Integer height,
        String profileImage
) {
    public UserDetail toEntity() { return UserDetail.createNewUserDetail(userId, nickname, gender, birthdate, height, profileImage); }

    public static UserDetailRequest of(Long userId, String nickname, String gender, String birthdate, Integer height, String profileImage) {
        return UserDetailRequest.builder()
                .userId(userId)
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height)
                .profileImage(profileImage)
                .build();
    }
}
