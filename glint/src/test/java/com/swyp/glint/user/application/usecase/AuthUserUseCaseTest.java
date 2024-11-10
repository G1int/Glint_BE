package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserLoginResponse;
import com.swyp.glint.user.application.dto.UserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;


@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user/auth-user-use-case-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
class AuthUserUseCaseTest {

    @Autowired
    private UserAuthUseCase userAuthUseCase;

    @Test
    @DisplayName("이미 가입한 회원이 있고 userDetail을 입력한경우 isComplete가 true인 UserLoginResponse를 반환한다.")
    void oauthLoginUserCompleteDetail() {
        // given
        UserRequest userRequest = UserRequest.of(
                "test@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        // when
        UserLoginResponse userLoginResponse = userAuthUseCase.oauthLoginUser(userRequest);

        // then
        assertThat(userLoginResponse.id()).isEqualTo(1L);
        assertThat(userLoginResponse.email()).isEqualTo("test@kakao.com");
        assertThat(userLoginResponse.isCompleteDetail()).isTrue();
    }


    @Test
    @DisplayName("회원가입이 되어있지 않을경우 isComplete가 false인 UserLoginResponse를 반환한다.")
    void oauthLoginUserIncompleteDetail() {
        // given
        UserRequest userRequest = UserRequest.of(
                "notuser@kakao.com",
                "ROLE_OAUTH_USER",
                "KAKAO"
        );

        // when
        UserLoginResponse userLoginResponse = userAuthUseCase.oauthLoginUser(userRequest);

        // then
        assertThat(userLoginResponse.email()).isEqualTo("notuser@kakao.com");
        assertThat(userLoginResponse.isCompleteDetail()).isFalse();
    }
}