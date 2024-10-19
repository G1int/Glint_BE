package com.swyp.glint.user.application.usecase;

import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.user.application.dto.UserDetailRequest;
import com.swyp.glint.user.application.dto.UserDetailResponse;
import com.swyp.glint.user.domain.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CreateUserDetailUseCaseTest {

    @Autowired
    private CreateUserDetailUseCase createUserDetailUseCase;


    @Test
    @DisplayName("userDetail을 저장 할 수 있다.")
    void createUserDetail() {

        //given
        UserDetailRequest userDetailRequest = UserDetailRequest.of(
                "nickname",
                Gender.MALE.name(),
                "2024-01-01",
                185,
                "profileImage"
        );

        //when
        UserDetailResponse userDetailResponse = createUserDetailUseCase.createUserDetail(1L, userDetailRequest);

        //then
        assertThat(userDetailResponse.userId()).isEqualTo(1L);
        assertThat(userDetailResponse.nickname()).isEqualTo("nickname");
        assertThat(userDetailResponse.gender()).isEqualTo("MALE");
        assertThat(userDetailResponse.birthdate()).isEqualTo("2024-01-01");
        assertThat(userDetailResponse.height()).isEqualTo(185);
        assertThat(userDetailResponse.profileImage()).isEqualTo("profileImage");

    }

    @Test
    @DisplayName("유저 nickname 중복검사시 nickname 선점을 위해 tempUserDetail을 생성한다.")
    void createTempUserDetail() {

        //given
        Long userId = 1L;
        String nickname = "nickname";

        //when
        UserDetailResponse userDetailResponse = createUserDetailUseCase.createTempUserDetail(userId, nickname);

        //then
        assertThat(userDetailResponse.userId()).isEqualTo(1L);
        assertThat(userDetailResponse.nickname()).isEqualTo("nickname");
        assertThat(userDetailResponse.gender()).isNull();
        assertThat(userDetailResponse.birthdate()).isNull();
        assertThat(userDetailResponse.height()).isNull();
        assertThat(userDetailResponse.profileImage()).isNull();
    }


    @Test
    @DisplayName("유저 nickname 중복검사시 nickname이 선점한 userId와 다른 userId가 요청되면 예외를 발생시킨다.")
    void createTempUserDetailNickNameValidateFail() {
        //given
        Long userId = 1L;
        String nickname = "nickname";

        //when
        //then
        assertThatThrownBy(() -> createUserDetailUseCase.createTempUserDetail(2L, nickname))
                .isInstanceOf(InvalidValueException.class);
    }
}