package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserDetail;
import lombok.Builder;

@Builder
public record UserDetailRequest(
        String nickname,
        String gender,
        String birthdate,
        Integer height,
        String profileImage
) {
    public UserDetail toEntity() { return UserDetail.createNewUserDetail(nickname, gender, birthdate, height, profileImage); }

    public static UserDetailRequest of(String nickname, String gender, String birthdate, Integer height, String profileImage) {
        return UserDetailRequest.builder()
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height)
                .profileImage(profileImage)
                .build();
    }
}
