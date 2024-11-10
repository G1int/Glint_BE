package com.swyp.glint.user.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UserSimpleProfile {

    private Long userId;
    private String profileImage;
    private String nickname;
    private String gender;
    private Integer age;
    private String affiliation;

    @Builder(access = AccessLevel.PRIVATE)
    private UserSimpleProfile(Long userId, String profileImage, String nickname, Integer age, String gender, String affiliation) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.affiliation = affiliation;
    }

    @QueryProjection
    public UserSimpleProfile(UserDetail userDetail, UserProfile userProfile) {
        this.userId = userDetail.getUserId();
        this.profileImage = userDetail.getProfileImage();
        this.age = userDetail.getAge();
        this.gender = userDetail.getGender();
        this.nickname = userDetail.getNickname();
        this.affiliation = Optional.ofNullable(userProfile)
                .map(UserProfile::getAffiliation)
                .orElse(null);
    }

    public static UserSimpleProfile of(UserDetail userDetail, UserProfile userProfile) {
        return UserSimpleProfile.builder()
                .userId(userDetail.getUserId())
                .profileImage(userDetail.getProfileImage())
                .age(userDetail.getAge())
                .gender(userDetail.getGender())
                .nickname(userDetail.getNickname())
                .affiliation(
                        Optional.ofNullable(userProfile)
                                .map(UserProfile::getAffiliation)
                                .orElse(null)
                )
                .build();
    }

}
