package com.swyp.glint.user.application.usecase;

import com.swyp.glint.core.common.exception.ErrorCode;
import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.user.application.dto.UserNickNameValidationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;


@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user/user-nickname-use-case-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
class UserNickNameUseCaseTest {

    @Autowired
    private UserNickNameUseCase userNickNameUseCase;

    @Test
    @DisplayName("중복된 닉넴임이 존재하는지 확인한다 - 중복된 닉네임이 존재하여 에러가 발생")
    void isNicknameTaken() {
        // given
        String nickname = "test";

        // when
        // then
        assertThatThrownBy(() -> userNickNameUseCase.isNicknameTaken(nickname))
                .isInstanceOf(InvalidValueException.class)
                .extracting("errorCode.status","errorCode.code","errorCode.message")
                .contains(400, ErrorCode.NICKNAME_DUPLICATED.getCode(), ErrorCode.NICKNAME_DUPLICATED.getMessage());
    }


    @Test
    @DisplayName("중복된 닉넴임이 존재하는지 확인한다 - 닉네임 유효성 검사해 실패 에러가 발생")
    void isNicknameTakenInvalidateNickname() {
        // given
        String nickname = "a";

        // when
        // then
        assertThatThrownBy(() -> userNickNameUseCase.isNicknameTaken(nickname))
                .isInstanceOf(InvalidValueException.class)
                .extracting("errorCode.status","errorCode.code","errorCode.message")
                .contains(400, ErrorCode.NICKNAME_INVALID.getCode(), ErrorCode.NICKNAME_INVALID.getMessage());
    }

    @Test
    @DisplayName("중복된 닉넴임이 존재하는지 확인한다 - 유효한 닉네임이 요청되어 성공")
    void isNicknameTakenSuccess() {
        // given
        String nickname = "success";

        // when
        UserNickNameValidationResponse userNickNameValidationResponse = userNickNameUseCase.isNicknameTaken(nickname);

        // then
        assertThat(userNickNameValidationResponse.nickname()).isEqualTo("success");
        assertThat(userNickNameValidationResponse.isAvailable()).isTrue();
    }



}