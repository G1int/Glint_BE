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

}
