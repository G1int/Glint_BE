package com.swyp.glint.user.business;

import com.swyp.glint.user.domain.Gender;
import com.swyp.glint.user.domain.UserDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailTest {


    @Test
    @DisplayName("닉네임을 변경할 수 있다.")
    public void updateNickname() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );
        //when
        userDetail.updateNickname("update");

        //then
        assertThat(userDetail.getNickname()).isEqualTo("update");
    }


    @Test
    @DisplayName("userDetail을 수정할 수 있다.")
    public void updateUserDetail() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        userDetail.updateUserDetail(
                "update",
                Gender.FEMALE.name(),
                LocalDate.of(1995, 01, 01),
                170,
                "updateProfileImage"
        );

        //then
        assertThat(userDetail.getNickname()).isEqualTo("update");
        assertThat(userDetail.getGender()).isEqualTo("FEMALE");
        assertThat(userDetail.getBirthdate()).isEqualTo(LocalDate.of(1995, 01, 01));
        assertThat(userDetail.getHeight()).isEqualTo(170);
        assertThat(userDetail.getProfileImage()).isEqualTo("updateProfileImage");
    }

    @Test
    @DisplayName("생일로 나이를 계산할 수 있다.")
    public void calculateAgeByBirthdate() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );
        //when
        Integer age = userDetail.calculateAgeByBirthdate();

        //then
        assertThat(age).isEqualTo(34);
    }

    @Test
    @DisplayName("성별이 같은지 확인할 수 있다.")
    public void sameGender() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean sameGender = userDetail.sameGender("MALE");

        //then
        assertThat(sameGender).isTrue();
    }

    @Test
    @DisplayName("나이가 범위 안에 있으면 True를 반환한다.")
    public void isAgeInTrue() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean ageIn = userDetail.isAgeIn(20, 40);

        //then
        assertThat(ageIn).isTrue();
    }

    @Test
    @DisplayName("나이가 범위 밖에 있으면 False를 반환한다.")
    public void isAgeInFalse() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean ageIn = userDetail.isAgeIn(10, 20);

        //then
        assertThat(ageIn).isFalse();
    }


    @Test
    @DisplayName("키가 범위 안에 있으면 True를 반환한다.")
    public void isHeightInTrue() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean heightIn = userDetail.isHeightIn(170, 180);

        //then
        assertThat(heightIn).isTrue();

    }

    @Test
    @DisplayName("키가 범위 밖에 있으면 False를 반환한다.")
    public void isHeightInFalse() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean heightIn = userDetail.isHeightIn(181, 190);

        //then
        assertThat(heightIn).isFalse();
    }

    @Test
    @DisplayName("성별이 FEMALE이면 True를 반환한다.")
    public void isFemale() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.FEMALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean isFemale = userDetail.isFemale();

        //then
        assertThat(isFemale).isTrue();
    }

    @Test
    @DisplayName("성별이 MALE이면 True를 반환한다.")
    public void isMale() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean isMale = userDetail.isMale();

        //then
        assertThat(isMale).isTrue();
    }



    @Test
    @DisplayName("profileImage를 변경할 수 있다.")
    public void updateProfileUrl() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        userDetail.updateProfileUrl("updateProfileImage");

        //then
        assertThat(userDetail.getProfileImage()).isEqualTo("updateProfileImage");
    }


    @Test
    @DisplayName("userDetail이 완성되지 않았으면 True를 반환한다.")
    public void isNotCompleteTrue() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                null
        );

        //when
        boolean isNotCompleteDetail = userDetail.isNotComplete();

        //then
        assertThat(isNotCompleteDetail).isTrue();
    }

    @Test
    @DisplayName("userDetail이 완성되었으면 False를 반환한다.")
    public void isNotCompleteFalse() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean isNotCompleteDetail = userDetail.isNotComplete();

        //then
        assertThat(isNotCompleteDetail).isFalse();
    }


    @Test
    @DisplayName("userDetail이 완성되지 않았으면 False를 반환한다.")
    public void isCompleteFalse() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                null
        );

        //when
        boolean isCompleteDetail = userDetail.isComplete();

        //then
        assertThat(isCompleteDetail).isFalse();
    }


    @Test
    @DisplayName("userDetail이 완성되었으면 True를 반환한다.")
    public void isCompleteTrue() {
        //given
        UserDetail userDetail = UserDetail.createNewUserDetail(
                1L,
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        //when
        boolean isCompleteDetail = userDetail.isComplete();

        //then
        assertThat(isCompleteDetail).isTrue();
    }
}
