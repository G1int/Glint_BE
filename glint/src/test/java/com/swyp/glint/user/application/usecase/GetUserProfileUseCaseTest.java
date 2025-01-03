package com.swyp.glint.user.application.usecase;

import com.swyp.glint.user.application.dto.UserProfileResponse;

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
        @Sql(value = "/sql/user/get-user-profile-use-case-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD),
})
class GetUserProfileUseCaseTest {

    @Autowired
    private GetUserProfileUseCase getUserProfileUseCase;

    @Test
    void getUserProfileBy() {
        // given
        Long userId = 1L;

        // when
        UserProfileResponse userProfileResponse = getUserProfileUseCase.getUserProfileBy(userId);

        // then
        assertThat(userProfileResponse.getId()).isEqualTo(1L);

        assertThat(userProfileResponse.getWork().getWorkId()).isEqualTo(1L);
        assertThat(userProfileResponse.getWork().getWorkName()).isEqualTo("개발자");
        assertThat(userProfileResponse.getWork().getWorkCategory()).isNull();

        assertThat(userProfileResponse.getUniversity().getUniversityId()).isEqualTo(1L);
        assertThat(userProfileResponse.getUniversity().getUniversityCategory().getUniversityCategoryId()).isEqualTo(1L);
        assertThat(userProfileResponse.getUniversity().getUniversityCategory().getUniversityCategoryName()).isEqualTo("명문대");
        assertThat(userProfileResponse.getUniversity().getUniversityDepartment()).isEqualTo("컴퓨터공학과");
        assertThat(userProfileResponse.getUniversity().getUniversityName()).isEqualTo("서울대학교");

        assertThat(userProfileResponse.getLocation().getLocationId()).isEqualTo(1);
        assertThat(userProfileResponse.getLocation().getLocationCity()).isEqualTo("종로구");
        assertThat(userProfileResponse.getLocation().getLocationState()).isEqualTo("서울");

        assertThat(userProfileResponse.getReligion().getReligionId()).isEqualTo(1L);
        assertThat(userProfileResponse.getReligion().getReligionName()).isEqualTo("무교");

        assertThat(userProfileResponse.getSmoking().getSmokingId()).isEqualTo(1L);
        assertThat(userProfileResponse.getSmoking().getSmokingName()).isEqualTo("비흡연");

        assertThat(userProfileResponse.getDrinking().getDrinkingId()).isEqualTo(1L);
        assertThat(userProfileResponse.getDrinking().getDrinkingName()).isEqualTo("마시지 않음");

        assertThat(userProfileResponse.getSelfIntroduction()).isEqualTo("안녕하세요 자기소개입니다.");
        assertThat(userProfileResponse.getHashtags()).contains("INTJ");
    }



}