package com.swyp.glint.user.domain;

import lombok.Getter;

@Getter
public class UserInfo {

    private final Long userId;
    private final UserDetail userDetail;
    private final UserProfile userProfile;


    public UserInfo(UserDetail userDetail, UserProfile userProfile) {
        this.userId = userDetail.getUserId();
        this.userDetail = userDetail;
        this.userProfile = userProfile;
    }


}
