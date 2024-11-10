package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user/get-user-use-case-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
class GetUserUseCaseTest {

    @Autowired
    private GetUserUseCase getUserUseCase;


   @Test
   @DisplayName("UserId를 통해 User를 조회할 수 있다.")
   public void getUserByUserId() {

       //given
       Long userId = 1L;
       //when
       UserResponse userResponse = getUserUseCase.getUserBy(userId);

       //then
       assertThat(userResponse.id()).isEqualTo(1L);
       assertThat(userResponse.email()).isEqualTo("test@kakao.com");
       assertThat(userResponse.provider()).isEqualTo("KAKAO");
       assertThat(userResponse.role()).isEqualTo("ROLE_OAUTH_USER");
   }

    @Test
    @DisplayName("user email을 통해 User를 조회할 수 있다.")
    public void getUserByUserEmail() {

        //given
        String email = "test@kakao.com";
        //when
        UserResponse userResponse = getUserUseCase.getUserBy(email);

        //then
        assertThat(userResponse.id()).isEqualTo(1L);
        assertThat(userResponse.email()).isEqualTo("test@kakao.com");
        assertThat(userResponse.provider()).isEqualTo("KAKAO");
        assertThat(userResponse.role()).isEqualTo("ROLE_OAUTH_USER");
    }
}