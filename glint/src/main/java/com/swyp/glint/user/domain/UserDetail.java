package com.swyp.glint.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birthdate", nullable = false)
    private String birthdate;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    private UserDetail(Long id, String nickname, String gender, String birthdate, Integer height, String profileImage) {
        this.id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.profileImage = profileImage;
    }

    public static UserDetail createNewUserDetail(String nickname, String gender, String birthdate, Integer height, String profileImage) {
        return UserDetail.builder()
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height)
                .profileImage(profileImage)
                .build();
    }

    public void updateUserDetail(String nickname, String gender, String birthdate, Integer height, String profileImage) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.profileImage = profileImage;
    }

}