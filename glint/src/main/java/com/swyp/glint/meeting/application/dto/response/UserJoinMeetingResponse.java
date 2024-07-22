package com.swyp.glint.meeting.application.dto.response;

import com.swyp.glint.user.domain.UserSimpleProfile;

public record UserJoinMeetingResponse(
        Long joinMeetingId,
        Long userId,
        String profileImage,
        String nickname,
        Integer age,
        String gender,
        String affiliation
) {


    public static UserJoinMeetingResponse from(Long joinMeetingId, UserSimpleProfile userSimpleProfile) {
        return new UserJoinMeetingResponse(
                joinMeetingId,
                userSimpleProfile.getUserId(),
                userSimpleProfile.getProfileImage(),
                userSimpleProfile.getNickname(),
                userSimpleProfile.getAge(),
                userSimpleProfile.getGender(),
                userSimpleProfile.getAffiliation()
        );
    }
}
