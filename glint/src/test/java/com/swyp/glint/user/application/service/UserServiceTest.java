package com.swyp.glint.user.application.service;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.user.domain.*;
import com.swyp.glint.user.infra.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    @DisplayName("유저 아이디로 유저 정보 가져오기")
    void getUserById() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User savedUser = userRepository.save(user);

        //when
        User foundUser = userService.getUserBy(savedUser.getId());

        //then
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getEmail()).isEqualTo("test@kakao.com");
        assertThat(foundUser.getRole()).isEqualTo("ROLE_OAUTH_USER");
        assertThat(foundUser.getProvider()).isEqualTo("KAKAO");

    }

    @Test
    @Transactional
    @DisplayName("유저 아이디로 유저 정보 조회 실패 NotFoundEntityException 발생")
    void getUserByIdFail() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User savedUser = userRepository.save(user);

        //when
        //then
        assertThrows(NotFoundEntityException.class, () -> userService.getUserBy(9999L));
    }

    @Test
    @Transactional
    @DisplayName("유저 이메일로 유저 정보 가져오기")
    void getUserByEmail() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User savedUser = userRepository.save(user);

        //when
        User foundUser = userService.getUserBy(savedUser.getEmail());

        //then
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getEmail()).isEqualTo("test@kakao.com");
        assertThat(foundUser.getRole()).isEqualTo("ROLE_OAUTH_USER");
        assertThat(foundUser.getProvider()).isEqualTo("KAKAO");

    }



    @Test
    @Transactional
    @DisplayName("유저 이메일로 유저 정보 조회 실패 NotFoundEntityException 발생")
    void getUserByEmailFail() {
        //given
        User user = User.createNewUser(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        User savedUser = userRepository.save(user);

        //when
        //then
        assertThrows(NotFoundEntityException.class, () -> userService.getUserBy("notEmail@kakao.com"));
    }


}