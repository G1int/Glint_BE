package com.swyp.glint.user.domain;

import com.swyp.glint.core.common.baseentity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

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

    @Column(name = "nickname", unique = true)
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

    public Integer calculateAgeByBirthdate() {
        if (birthdate == null) {
            return null;
            //throw new IllegalArgumentException("Birthdate can not be null");
        }
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public boolean sameGender(String gender) {
        return this.getGender().equals(gender);
    }

    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public boolean isAgeIn(Integer minAge, Integer maxAge) {
        int age = calculateAge(this.birthdate);
        return age >= minAge && age <= maxAge;
    }

    public boolean isHeightIn(Integer minHeight, Integer maxHeight) {
        return height >= minHeight && height <= maxHeight;
    }

    public Integer getAge() {
        return calculateAge(birthdate);
    }

    public boolean isFemale() {
        return gender.equals("FEMALE");
    }
    public boolean isMale() {
        return gender.equals("MALE");
    }

    public void updateProfileUrl(String userProfileImageUrl) {
        this.profileImage = userProfileImageUrl;
    }

    public boolean isComplete() {
        return Objects.nonNull(nickname) &&
                Objects.nonNull(gender) &&
                Objects.nonNull(birthdate) &&
                Objects.nonNull(height) &&
                Objects.nonNull(profileImage);
    }

    public boolean isNotComplete() {
        return Objects.isNull(nickname) ||
                Objects.isNull(gender) ||
                Objects.isNull(birthdate) ||
                Objects.isNull(height) ||
                Objects.isNull(profileImage);
    }
}