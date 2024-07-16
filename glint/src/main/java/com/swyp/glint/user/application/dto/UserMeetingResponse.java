package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetailAggregation;
import lombok.Builder;

@Builder
public record UserMeetingResponse(
        Long id,
        String profileImage,
        String nickname,
        String gender,
        //직장, 학교
        String affiliation
) {

    public UserMeetingResponse(Long id, String profileImage, String nickname, String gender) {
        this(id, profileImage, nickname, gender, "");
    }

    public static UserMeetingResponse from(UserDetailAggregation userDetailAggregation) {
        return UserMeetingResponse.builder()
                .id(userDetailAggregation.getUserId())
                .profileImage(userDetailAggregation.getProfileImage())
                .nickname(userDetailAggregation.getNickname())
                .gender(userDetailAggregation.getGender())
                .affiliation("삼성전자")
//                .affiliation(userDetailAggregation.getAffiliation())
                .build();
    }
}
