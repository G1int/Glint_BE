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
}