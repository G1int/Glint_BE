package com.swyp.glint.user.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserMeeting extends BaseTimeEntity {

    private Long userId;
    private String profileImage;
    private String nickname;
    private Integer age;
    private String gender;
    //직장, 학교
    private String affiliation;

    public UserMeeting(Long userId, String profileImage, String nickname, Integer age, String gender) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.age = age;
        this.gender = gender;
        this.nickname = nickname;
        this.affiliation = "삼성전자";
    }

}
