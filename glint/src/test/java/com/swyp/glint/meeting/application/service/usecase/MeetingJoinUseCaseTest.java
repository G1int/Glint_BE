package com.swyp.glint.meeting.application.service.usecase;

import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.JoinMeetingResponse;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.usecase.GetMeetingUseCase;
import com.swyp.glint.meeting.application.usecase.MeetingJoinUseCase;
import com.swyp.glint.meeting.exception.AlreadyJoinMeetingException;
import com.swyp.glint.user.application.dto.DrinkingResponse;
import com.swyp.glint.user.application.dto.ReligionResponse;
import com.swyp.glint.user.application.dto.SmokingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Transactional
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/meeting/meeting-join-usecase-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD),
})
class MeetingJoinUseCaseTest {

    @Autowired
    private MeetingJoinUseCase meetingJoinUseCase;

    @Test
    @DisplayName("유저는 미팅방에 참가 요청을 할 수 있다.")
    void joinMeeting() {

        // given
        Long meetingId = 1L;
        Long userId = 2L;

        // when
        JoinMeetingResponse joinMeetingResponse = meetingJoinUseCase.joinMeetingRequest(userId, meetingId);

        // then

        assertThat(joinMeetingResponse.id()).isNotNull();
        assertThat(joinMeetingResponse.userId()).isEqualTo(2L);
        assertThat(joinMeetingResponse.meetingId()).isEqualTo(1L);
        assertThat(joinMeetingResponse.status()).isEqualTo("WAITING");

    }

    @Test
    @DisplayName("유저는 미팅방에 참가 조건에 맞지 않으면 참가 신청을 할 수 없다.")
    void joinMeetingFailWhenNotMatchCondition() {
        // given
        Long meetingId = 1L;
        Long userId = 3L;

        // when
        // then
        assertThatThrownBy(() -> meetingJoinUseCase.joinMeetingRequest(userId, meetingId))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Not Match Meeting Condition");
    }


    @Test
    @DisplayName("유저는 이미 참가한 미팅은 참가할 수 없다.")
    void getMeetingFailAlreadyJoinMeeting() {

        // given
        Long meetingId = 1L;
        Long userId = 1L;

        // when
        // then
        assertThatThrownBy(() -> meetingJoinUseCase.joinMeetingRequest(userId, meetingId))
                .isInstanceOf(AlreadyJoinMeetingException.class)
                .hasMessage("이미 참가한 미팅입니다.");

    }

    @Test
    @DisplayName("유저는 미팅방에 참가 요청을 수락할 수 있다.")
    public void acceptJoinMeeting() {
        // given
        Long meetingId = 1L;
        Long userId = 3L;

        // when
        JoinMeetingResponse joinMeetingResponse = meetingJoinUseCase.acceptJoinMeeting(userId, meetingId);

        // then
        assertThat(joinMeetingResponse.id()).isNotNull();
        assertThat(joinMeetingResponse.userId()).isEqualTo(3L);
        assertThat(joinMeetingResponse.meetingId()).isEqualTo(1L);
        assertThat(joinMeetingResponse.status()).isEqualTo("ACCEPTED");
    }

    @Test
    @DisplayName("유저는 미팅방 참가 거절당할 수 있다.")
    public void rejectJoinMeeting() {
        // given
        Long meetingId = 1L;
        Long userId = 3L;

        // when
        JoinMeetingResponse joinMeetingResponse = meetingJoinUseCase.rejectJoinMeeting(userId, meetingId);

        // then
        assertThat(joinMeetingResponse.id()).isNotNull();
        assertThat(joinMeetingResponse.userId()).isEqualTo(3L);
        assertThat(joinMeetingResponse.meetingId()).isEqualTo(1L);
        assertThat(joinMeetingResponse.status()).isEqualTo("REJECTED");
    }


}