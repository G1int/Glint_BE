package com.swyp.glint.user.application.dto;

import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetailAggregation;
import com.swyp.glint.user.domain.UserMeeting;
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

    public static UserMeetingResponse from(UserMeeting userMeeting) {
        return UserMeetingResponse.builder()
                .id(userMeeting.getUserId())
                .profileImage(userMeeting.getProfileImage())
                .nickname(userMeeting.getNickname())
                .gender(userMeeting.getGender())
                .affiliation("삼성전자")
//                .affiliation(userDetailAggregation.getAffiliation())
                .build();
    }
}
