package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.Builder;

@Builder
public record UserMeetingResponse(
        Long id,
        String profileImage,
        String nickname,
        String gender,
        String affiliation
) {

    public UserMeetingResponse(Long id, String profileImage, String nickname, String gender) {
        this(id, profileImage, nickname, gender, "");
    }

    public static UserMeetingResponse from(UserSimpleProfile userSimpleProfile) {
        return UserMeetingResponse.builder()
                .id(userSimpleProfile.getUserId())
                .profileImage(userSimpleProfile.getProfileImage())
                .nickname(userSimpleProfile.getNickname())
                .gender(userSimpleProfile.getGender())
                .affiliation(userSimpleProfile.getAffiliation())
                .build();
    }
}
