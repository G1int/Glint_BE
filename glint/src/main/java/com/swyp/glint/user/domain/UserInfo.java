package com.swyp.glint.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfo {

    private final Long userId;
    private final UserDetail userDetail;
    private final UserProfile userProfile;


    @Builder(access = AccessLevel.PRIVATE)
    private UserInfo(UserDetail userDetail, UserProfile userProfile) {
        this.userId = userDetail.getUserId();
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }




    public static UserInfo of(UserDetail userDetail, UserProfile userProfile) {
        return UserInfo.builder()
                .userDetail(userDetail)
                .userProfile(userProfile)
                .build();
    }


}
