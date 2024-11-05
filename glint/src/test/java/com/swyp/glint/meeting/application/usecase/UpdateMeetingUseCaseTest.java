package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.meeting.application.dto.request.JoinConditionRequest;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.usecase.UpdateMeetingUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Transactional
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/meeting/update-meeting-usecase-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD),
})
class UpdateMeetingUseCaseTest {

    @Autowired
    private UpdateMeetingUseCase updateMeetingUseCase;

    @Test
    @DisplayName("미팅 리더는 미팅을 수정 할 수 있다.")
    void updateMeeting() {

        // given
        Long meetingId = 1L;
        MeetingRequest meetingRequest = new MeetingRequest (
                "서울에서 미팅해요",
                "서울이면 어디든 좋아요, 나이만 맞으면 가능!",
                1L,
                new ArrayList<>(List.of(1L)),
                new JoinConditionRequest(new ArrayList<>(List.of("AGE")), new ArrayList<>(),28,35, 200, 140, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                new JoinConditionRequest(new ArrayList<>(List.of("AGE")), new ArrayList<>(),25, 30, 200, 140, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()),
                "4"
        );


        // when
        MeetingDetailResponse meetingDetailResponse = updateMeetingUseCase.updateMeeting(
                meetingId,
                meetingRequest
        );

        // then

        assertThat(meetingDetailResponse).isNotNull();
        assertThat(meetingDetailResponse.id()).isNotNull();
        assertThat(meetingDetailResponse.leaderUserId()).isEqualTo(1L);
        assertThat(meetingDetailResponse.title()).isEqualTo("서울에서 미팅해요");
        assertThat(meetingDetailResponse.description()).isEqualTo("서울이면 어디든 좋아요, 나이만 맞으면 가능!");
        assertThat(meetingDetailResponse.users()).hasSize(1);
        assertThat(meetingDetailResponse.locations()).containsExactly("서울 종로구");

        assertThat(meetingDetailResponse.maleCondition().selectConditions()).containsExactly("AGE");
        assertThat(meetingDetailResponse.maleCondition().affiliation()).isEmpty();
        assertThat(meetingDetailResponse.maleCondition().maxAge()).isEqualTo(35);
        assertThat(meetingDetailResponse.maleCondition().minAge()).isEqualTo(28);
        assertThat(meetingDetailResponse.maleCondition().maxHeight()).isEqualTo(200);
        assertThat(meetingDetailResponse.maleCondition().minHeight()).isEqualTo(140);
        assertThat(meetingDetailResponse.maleCondition().religion()).isEmpty();
        assertThat(meetingDetailResponse.maleCondition().smoking()).isEmpty();
        assertThat(meetingDetailResponse.maleCondition().drinking()).isEmpty();

        assertThat(meetingDetailResponse.femaleCondition().selectConditions()).containsExactly("AGE");
        assertThat(meetingDetailResponse.femaleCondition().affiliation()).isEmpty();
        assertThat(meetingDetailResponse.femaleCondition().maxAge()).isEqualTo(30);
        assertThat(meetingDetailResponse.femaleCondition().minAge()).isEqualTo(25);
        assertThat(meetingDetailResponse.femaleCondition().maxHeight()).isEqualTo(200);
        assertThat(meetingDetailResponse.femaleCondition().minHeight()).isEqualTo(140);
        assertThat(meetingDetailResponse.femaleCondition().religion()).isEmpty();
        assertThat(meetingDetailResponse.femaleCondition().smoking()).isEmpty();
        assertThat(meetingDetailResponse.femaleCondition().drinking()).isEmpty();


        assertThat(meetingDetailResponse.peopleCapacity()).isEqualTo(4);
        assertThat(meetingDetailResponse.status()).isEqualTo("WAITING");
        assertThat(meetingDetailResponse.joinRequestUserIds()).hasSize(1);


    }




}