package com.swyp.glint.meeting.application.service.usecase;

import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.usecase.UserMeetingUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Transactional
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/meeting/user-meeting-usecase-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD),
})
class UserMeetingUseCaseTest {

    @Autowired
    private UserMeetingUseCase userMeetingUseCase;

    @Test
    @DisplayName("미팅 참가자는 미팅방에서 나갈 수 있다.")
    void userMeetingOut() {

        // given
        Long meetingId = 1L;
        Long userId = 2L;

        // when
        MeetingDetailResponse meetingDetailResponse = userMeetingUseCase.userMeetingOut(
                meetingId,
                userId
        );

        // then
        assertThat(meetingDetailResponse).isNotNull();
        assertThat(meetingDetailResponse.id()).isNotNull();
        assertThat(meetingDetailResponse.leaderUserId()).isEqualTo(1L);
        assertThat(meetingDetailResponse.users()).extracting("id").contains(1L,3L);
    }


    @Test
    @DisplayName("유저가 참가중인 모든 미팅을 나갈 수 있다.")
    public void deleteUserMeeting() {

            // given
            Long userId = 1L;

            // when
            userMeetingUseCase.deleteUserMeeting(userId);

            // then
            // 유저가 참가중인 모든 미팅을 나갔으므로, 유저가 참가중인 미팅이 없어야 한다.
            // 유저가 참가중인 미팅이 없으므로, 유저가 참가중인 미팅이 없어야 한다.
    }



}