package com.swyp.glint.meeting.application.usecase;

import com.swyp.glint.core.common.exception.InvalidValueException;
import com.swyp.glint.meeting.application.dto.request.JoinConditionRequest;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.usecase.CreateMeetingUseCase;
import com.swyp.glint.user.application.dto.SmokingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/meeting/create-meeting-usecase-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD),
})
public class CreateMeetingUseCaseTest {

    @Autowired
    private CreateMeetingUseCase createMeetingUseCase;

    @Test
    @DisplayName("미팅방을 생성할 수 있다.")
    public void createMeeting() {
        // given
        MeetingRequest meetingRequest = new MeetingRequest (
                "다모여라",
                "모두 모여모라",
                1L,
                List.of(1L),
                new JoinConditionRequest(List.of("AGE", "HEIGHT", "SMOKING"), List.of("삼성전자"),20,30, 200, 140, List.of(), List.of(1L), List.of()),
                new JoinConditionRequest(List.of("AGE", "HEIGHT"), List.of("서울대학교"),20, 30, 200, 140, List.of(), List.of(), List.of()),
                "4"
        );

        // when
        MeetingDetailResponse meetingDetailResponse = createMeetingUseCase.createMeeting(meetingRequest);

        // then

        assertThat(meetingDetailResponse).isNotNull();
        assertThat(meetingDetailResponse.id()).isNotNull();
        assertThat(meetingDetailResponse.leaderUserId()).isEqualTo(1L);
        assertThat(meetingDetailResponse.title()).isEqualTo("다모여라");
        assertThat(meetingDetailResponse.description()).isEqualTo("모두 모여모라");
        assertThat(meetingDetailResponse.users()).hasSize(1);
        assertThat(meetingDetailResponse.users().get(0).id()).isEqualTo(1L);
        assertThat(meetingDetailResponse.users().get(0).profileImage()).isEqualTo("test.jpg");
        assertThat(meetingDetailResponse.users().get(0).nickname()).isEqualTo("test");
        assertThat(meetingDetailResponse.users().get(0).gender()).isEqualTo("MALE");
        assertThat(meetingDetailResponse.users().get(0).age()).isEqualTo(24);
        assertThat(meetingDetailResponse.users().get(0).affiliation()).isEqualTo("삼성전자");
        assertThat(meetingDetailResponse.locations()).containsExactly("서울 종로구");

        assertThat(meetingDetailResponse.maleCondition().selectConditions()).containsExactly("AGE", "HEIGHT", "SMOKING");
        assertThat(meetingDetailResponse.maleCondition().affiliation()).containsExactly("삼성전자");
        assertThat(meetingDetailResponse.maleCondition().maxAge()).isEqualTo(30);
        assertThat(meetingDetailResponse.maleCondition().minAge()).isEqualTo(20);
        assertThat(meetingDetailResponse.maleCondition().maxHeight()).isEqualTo(200);
        assertThat(meetingDetailResponse.maleCondition().minHeight()).isEqualTo(140);
        assertThat(meetingDetailResponse.maleCondition().religion()).isEmpty();
        assertThat(meetingDetailResponse.maleCondition().smoking()).containsExactly(new SmokingResponse(1L, "비흡연"));
        assertThat(meetingDetailResponse.maleCondition().drinking()).isEmpty();

        assertThat(meetingDetailResponse.femaleCondition().selectConditions()).containsExactly("AGE", "HEIGHT");
        assertThat(meetingDetailResponse.femaleCondition().affiliation()).containsExactly("서울대학교");
        assertThat(meetingDetailResponse.femaleCondition().maxAge()).isEqualTo(30);
        assertThat(meetingDetailResponse.femaleCondition().minAge()).isEqualTo(20);
        assertThat(meetingDetailResponse.femaleCondition().maxHeight()).isEqualTo(200);
        assertThat(meetingDetailResponse.femaleCondition().minHeight()).isEqualTo(140);
        assertThat(meetingDetailResponse.femaleCondition().religion()).isEmpty();
        assertThat(meetingDetailResponse.femaleCondition().smoking()).isEmpty();
        assertThat(meetingDetailResponse.femaleCondition().drinking()).isEmpty();


        assertThat(meetingDetailResponse.peopleCapacity()).isEqualTo(4);
        assertThat(meetingDetailResponse.status()).isEqualTo("WAITING");
        assertThat(meetingDetailResponse.joinRequestUserIds()).isEmpty();
    }


    @Test
    @DisplayName("설정한 조건에 맞지 않는 리더는 미팅방을 생성할 수 없다.")
    public void createMeetingFailLeaderNotMathCondition() {
        // given
        MeetingRequest meetingRequest = new MeetingRequest (
                "다모여라",
                "모두 모여모라",
                1L,
                List.of(1L),
                new JoinConditionRequest(List.of("AGE", "HEIGHT", "SMOKING"), List.of("삼성전자"),30, 140, 200, 20, List.of(), List.of(1L), List.of()),
                new JoinConditionRequest(List.of("AGE", "HEIGHT"),List.of("서울대학교"),30, 140, 200, 20, List.of(), List.of(), List.of()),
                "4"
        );

        // when
        // then
        assertThatThrownBy(() -> createMeetingUseCase.createMeeting(meetingRequest))
                .isInstanceOf(InvalidValueException.class)
                .hasMessage("Not Match Meeting Condition");
    }

}
