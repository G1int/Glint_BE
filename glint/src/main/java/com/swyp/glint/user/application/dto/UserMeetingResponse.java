package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.UserSimpleProfile;
import lombok.Builder;

@Builder
public record UserMeetingResponse(
        Long id,
        String profileImage,
        String nickname,
        String gender,
        Integer age,
        String affiliation
) {


    public static UserMeetingResponse from(UserSimpleProfile userSimpleProfile) {
        return UserMeetingResponse.builder()
                .id(userSimpleProfile.getUserId())
                .profileImage(userSimpleProfile.getProfileImage())
                .nickname(userSimpleProfile.getNickname())
                .gender(userSimpleProfile.getGender())
                .age(userSimpleProfile.getAge())
                .affiliation(userSimpleProfile.getAffiliation())
                .build();
    }
}
