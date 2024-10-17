package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserDetailResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.*;


@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/get-user-detail-use-case-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
class GetUserDetailUseCaseTest {

    @Autowired
    private GetUserDetailUseCase getUserDetailUseCase;

    @Test
    void getUserDetailBy() {
        // given
        Long userId = 1L;

        // when
        UserDetailResponse userDetailResponse = getUserDetailUseCase.getUserDetailBy(userId);

        // then
        assertThat(userDetailResponse.userId()).isEqualTo(1L);
    }
}