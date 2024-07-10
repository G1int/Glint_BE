package com.swyp.glint.user.domain;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserDetailAggregation {

    private Long userId;


    private String email;

    private String role;

    private String provider;

    private Boolean archived;

    private String nickname;

    private String gender;

    private LocalDate birthdate;

    private Integer height;

    private String profileImage;

    @Builder(access = lombok.AccessLevel.PRIVATE)
    private UserDetailAggregation(Long userId, String email, String role, String provider, Boolean archived, String nickname, String gender, LocalDate birthdate, Integer height, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.archived = archived;
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.profileImage = profileImage;
    }

    public static UserDetailAggregation of(User user, UserDetail userDetail) {
        return UserDetailAggregation.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .provider(user.getProvider())
                .archived(user.getArchived())
                .nickname(userDetail.getNickname())
                .gender(userDetail.getGender())
                .birthdate(userDetail.getBirthdate())
                .height(userDetail.getHeight())
                .profileImage(userDetail.getProfileImage())
                .build();
    }
}
