package com.swyp.glint.user.domain;

import com.swyp.glint.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetail extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "height")
    private Integer height;

    @Column(name = "profile_image")
    private String profileImage;

    @Builder(access = AccessLevel.PRIVATE)
    private UserDetail(Long id, Long userId, String nickname, String gender, LocalDate birthdate, Integer height, String profileImage) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.profileImage = profileImage;
    }

    public static UserDetail createNewUserDetail(Long userId, String nickname, String gender, LocalDate birthdate, Integer height, String profileImage) {
        return UserDetail.builder()
                .userId(userId)
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height)
                .profileImage(profileImage)
                .build();
    }

    public static UserDetail createTempUserDetailByNickName(Long userId) {
        return UserDetail.builder()
                .userId(userId)
                .build();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateUserDetail(String nickname, String gender, LocalDate birthdate, Integer height, String profileImage) {
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.height = height;
        this.profileImage = profileImage;
    }


}