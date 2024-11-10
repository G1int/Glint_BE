package com.swyp.glint.user.application.service;

import com.swyp.glint.user.application.impl.UserDetailService;
import com.swyp.glint.user.domain.Gender;
import com.swyp.glint.user.domain.User;
import com.swyp.glint.user.domain.UserDetail;
import com.swyp.glint.user.repository.UserDetailRepository;
import com.swyp.glint.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserDetailServiceTest {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailService userDetailService;


    @Test
    @Transactional
    @DisplayName("유저 아이디로 유저 Detail 정보 가져오기")
    void getUserDetailByUserId() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User savedUser = userRepository.save(user);

        UserDetail userDetail = UserDetail.createNewUserDetail(
                savedUser.getId(),
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        UserDetail savedUserDetail = userDetailRepository.save(userDetail);


        //when
        UserDetail foundUserDetail = userDetailService.getUserDetailBy(savedUser.getId());

        //then
        assertThat(foundUserDetail.getId()).isEqualTo(savedUserDetail.getId());
        assertThat(foundUserDetail.getUserId()).isEqualTo(savedUser.getId());
        assertThat(foundUserDetail.getNickname()).isEqualTo("nickname");
        assertThat(foundUserDetail.getGender()).isEqualTo(Gender.MALE.name());
        assertThat(foundUserDetail.getBirthdate()).isEqualTo(LocalDate.of(1990, 01, 01));
        assertThat(foundUserDetail.getHeight()).isEqualTo(180);
        assertThat(foundUserDetail.getProfileImage()).isEqualTo("profileImage");
    }


    @Test
    @Transactional
    @DisplayName("유저 닉네임으로 유저 Detail 정보 가져오기")
    void getUserDetailByEmail() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User savedUser = userRepository.save(user);

        UserDetail userDetail = UserDetail.createNewUserDetail(
                savedUser.getId(),
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        UserDetail savedUserDetail = userDetailRepository.save(userDetail);


        //when
        Optional<UserDetail> userDetailOptional = userDetailService.findBy(savedUserDetail.getNickname());

        //then
        assertThat(userDetailOptional).isPresent();
        UserDetail foundUserDetail = userDetailOptional.get();
        assertThat(foundUserDetail.getId()).isEqualTo(savedUserDetail.getId());
        assertThat(foundUserDetail.getUserId()).isEqualTo(savedUser.getId());
        assertThat(foundUserDetail.getNickname()).isEqualTo("nickname");
        assertThat(foundUserDetail.getGender()).isEqualTo(Gender.MALE.name());
        assertThat(foundUserDetail.getBirthdate()).isEqualTo(LocalDate.of(1990, 01, 01));
        assertThat(foundUserDetail.getHeight()).isEqualTo(180);
        assertThat(foundUserDetail.getProfileImage()).isEqualTo("profileImage");
    }


    @Test
    @Transactional
    @DisplayName("유저 id리스트로 여러개의 유저 Detail 정보 가져올 수 있다.")
    void getUserDetailByUserIds() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User user2 = User.createNewUser(
                "test2@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        List<User> users = userRepository.saveAll(List.of(user, user2));

        UserDetail userDetail = UserDetail.createNewUserDetail(
                users.get(0).getId(),
                "nickname",
                Gender.MALE.name(),
                LocalDate.of(1990, 01, 01),
                180,
                "profileImage"
        );

        UserDetail userDetail2 = UserDetail.createNewUserDetail(
                users.get(1).getId(),
                "nickname2",
                Gender.FEMALE.name(),
                LocalDate.of(1995, 01, 01),
                160,
                "profileImage"
        );


        List<UserDetail> userDetails = userDetailRepository.saveAll(List.of(userDetail, userDetail2));


        //when
        List<UserDetail> foundUserDetails = userDetailService.getUserDetails(List.of(users.get(0).getId(), users.get(1).getId()));

        //then
        assertThat(foundUserDetails).size().isEqualTo(2);

        assertThat(foundUserDetails.get(0).getId()).isEqualTo(userDetails.get(0).getId());
        assertThat(foundUserDetails.get(0).getUserId()).isEqualTo(users.get(0).getId());
        assertThat(foundUserDetails.get(0).getNickname()).isEqualTo("nickname");
        assertThat(foundUserDetails.get(0).getGender()).isEqualTo(Gender.MALE.name());
        assertThat(foundUserDetails.get(0).getBirthdate()).isEqualTo(LocalDate.of(1990, 01, 01));
        assertThat(foundUserDetails.get(0).getHeight()).isEqualTo(180);
        assertThat(foundUserDetails.get(0).getProfileImage()).isEqualTo("profileImage");

        assertThat(foundUserDetails.get(1).getId()).isEqualTo(userDetails.get(1).getId());
        assertThat(foundUserDetails.get(1).getUserId()).isEqualTo(users.get(1).getId());
        assertThat(foundUserDetails.get(1).getNickname()).isEqualTo("nickname2");
        assertThat(foundUserDetails.get(1).getGender()).isEqualTo(Gender.FEMALE.name());
        assertThat(foundUserDetails.get(1).getBirthdate()).isEqualTo(LocalDate.of(1995, 01, 01));
        assertThat(foundUserDetails.get(1).getHeight()).isEqualTo(160);
        assertThat(foundUserDetails.get(1).getProfileImage()).isEqualTo("profileImage");
    }


}