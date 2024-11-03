package com.swyp.glint.meeting.application.service.usecase;

import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingDetailResponse;
import com.swyp.glint.meeting.application.usecase.GetMeetingUseCase;
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
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Transactional
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/meeting/get-meeting-usecase-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD),
})
class GetMeetingUseCaseTest {

    @Autowired
    private GetMeetingUseCase getMeetingUseCase;

    @Test
    @DisplayName("특정 미팅방을 조회할 수 있다.")
    void getMeeting() {

        // given
        Long meetingId = 1L;

        // when
        MeetingDetailResponse meetingDetailResponse = getMeetingUseCase.getMeeting(meetingId);

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
        assertThat(meetingDetailResponse.maleCondition().affiliation()).containsExactly("삼성전자", "서울대학교");
        assertThat(meetingDetailResponse.maleCondition().maxAge()).isEqualTo(30);
        assertThat(meetingDetailResponse.maleCondition().minAge()).isEqualTo(20);
        assertThat(meetingDetailResponse.maleCondition().maxHeight()).isEqualTo(200);
        assertThat(meetingDetailResponse.maleCondition().minHeight()).isEqualTo(140);
        assertThat(meetingDetailResponse.maleCondition().religion()).containsExactly(new ReligionResponse(1L, "무교"));
        assertThat(meetingDetailResponse.maleCondition().smoking()).containsExactly(new SmokingResponse(1L, "비흡연"));
        assertThat(meetingDetailResponse.maleCondition().drinking()).containsExactly(new DrinkingResponse(1L, "마시지 않음"));

        assertThat(meetingDetailResponse.femaleCondition().selectConditions()).containsExactly("AGE", "HEIGHT");
        assertThat(meetingDetailResponse.femaleCondition().affiliation()).containsExactly("서울대학교");
        assertThat(meetingDetailResponse.femaleCondition().maxAge()).isEqualTo(30);
        assertThat(meetingDetailResponse.femaleCondition().minAge()).isEqualTo(20);
        assertThat(meetingDetailResponse.femaleCondition().maxHeight()).isEqualTo(200);
        assertThat(meetingDetailResponse.femaleCondition().minHeight()).isEqualTo(140);
        assertThat(meetingDetailResponse.femaleCondition().religion()).containsExactly(new ReligionResponse(1L, "무교"));
        assertThat(meetingDetailResponse.femaleCondition().smoking()).containsExactly(new SmokingResponse(1L, "비흡연"));
        assertThat(meetingDetailResponse.femaleCondition().drinking()).containsExactly(new DrinkingResponse(1L, "마시지 않음"));


        assertThat(meetingDetailResponse.peopleCapacity()).isEqualTo(4);
        assertThat(meetingDetailResponse.status()).isEqualTo("WAITING");
        assertThat(meetingDetailResponse.joinRequestUserIds()).contains(2L);

    }

    @Test
    @DisplayName("새로운 미팅방 리스트를 조회할 수 있다.")
    void getNewMeeting() {

            // given
            Long lastId = null;
            Integer size = 1;

            // when
            var meetingInfoResponses = getMeetingUseCase.getNewMeeting(lastId, size);

            // then
            assertThat(meetingInfoResponses).isNotNull();
            assertThat(meetingInfoResponses.meetings()).hasSize(1);
            assertThat(meetingInfoResponses.meetings().get(0).meetingId()).isEqualTo(1L);
            assertThat(meetingInfoResponses.meetings().get(0).maleCount()).isEqualTo(1L);
            assertThat(meetingInfoResponses.meetings().get(0).femaleCount()).isEqualTo(0L);
            assertThat(meetingInfoResponses.meetings().get(0).maleAgeRange()).satisfies(
                    ageRangeResponse -> {
                        assertThat(ageRangeResponse.minAge()).isEqualTo(20);
                        assertThat(ageRangeResponse.maxAge()).isEqualTo(30);
                    }
            );
            assertThat(meetingInfoResponses.meetings().get(0).femaleAgeRange()).satisfies(
                    ageRangeResponse -> {
                        assertThat(ageRangeResponse.minAge()).isEqualTo(20);
                        assertThat(ageRangeResponse.maxAge()).isEqualTo(30);
                    }
            );
            assertThat(meetingInfoResponses.meetings().get(0).title()).isEqualTo("다모여라");
            assertThat(meetingInfoResponses.meetings().get(0).status()).isEqualTo("WAITING");
            assertThat(meetingInfoResponses.meetings().get(0).meetingImage()).isEqualTo("https://glint.com/image.png");
            assertThat(meetingInfoResponses.meetings().get(0).locations()).containsExactly("서울");
            assertThat(meetingInfoResponses.meetings().get(0).peopleCapacity()).isEqualTo(4);
    }

    @Test
    @DisplayName("내가 참가한 미팅 리스트를 조회 할 수 있다.")
    void getMyMeeting() {

        // given
        Long lastId = null;
        String meetingStatus = "WAITING";
        Long userId = 1L;
        Integer limit = 10;

        // when
        var meetingInfoResponses = getMeetingUseCase.getMyMeeting(userId, meetingStatus, lastId, limit);

        // then
        assertThat(meetingInfoResponses).isNotNull();
        assertThat(meetingInfoResponses.meetings()).hasSize(1);
        assertThat(meetingInfoResponses.meetings().get(0).meetingId()).isEqualTo(1L);
        assertThat(meetingInfoResponses.meetings().get(0).maleCount()).isEqualTo(1L);
        assertThat(meetingInfoResponses.meetings().get(0).femaleCount()).isEqualTo(0L);
        assertThat(meetingInfoResponses.meetings().get(0).maleAgeRange()).satisfies(
                ageRangeResponse -> {
                    assertThat(ageRangeResponse.minAge()).isEqualTo(20);
                    assertThat(ageRangeResponse.maxAge()).isEqualTo(30);
                }
        );
        assertThat(meetingInfoResponses.meetings().get(0).femaleAgeRange()).satisfies(
                ageRangeResponse -> {
                    assertThat(ageRangeResponse.minAge()).isEqualTo(20);
                    assertThat(ageRangeResponse.maxAge()).isEqualTo(30);
                }
        );
        assertThat(meetingInfoResponses.meetings().get(0).title()).isEqualTo("다모여라");
        assertThat(meetingInfoResponses.meetings().get(0).status()).isEqualTo("WAITING");
        assertThat(meetingInfoResponses.meetings().get(0).meetingImage()).isEqualTo("https://glint.com/image.png");
        assertThat(meetingInfoResponses.meetings().get(0).locations()).containsExactly("서울");
        assertThat(meetingInfoResponses.meetings().get(0).peopleCapacity()).isEqualTo(4);
    }

    @Test
    @DisplayName("등록한 키워드를 통해 미팅 검색을 할 수 있다.")
    void searchMeeting() {

        // given
        MeetingSearchCondition searchCondition = new MeetingSearchCondition("모여",null,10, List.of(1L));

        // when
        var meetingInfoResponses = getMeetingUseCase.searchMeeting(searchCondition, 1L);

        // then
        assertThat(meetingInfoResponses).isNotNull();
        assertThat(meetingInfoResponses.meetings()).hasSize(1);
        assertThat(meetingInfoResponses.meetings().get(0).meetingId()).isEqualTo(1L);
        assertThat(meetingInfoResponses.meetings().get(0).maleCount()).isEqualTo(1L);
        assertThat(meetingInfoResponses.meetings().get(0).femaleCount()).isEqualTo(0L);
        assertThat(meetingInfoResponses.meetings().get(0).maleAgeRange()).satisfies(
                ageRangeResponse -> {
                    assertThat(ageRangeResponse.minAge()).isEqualTo(20);
                    assertThat(ageRangeResponse.maxAge()).isEqualTo(30);
                }
        );
        assertThat(meetingInfoResponses.meetings().get(0).femaleAgeRange()).satisfies(
                ageRangeResponse -> {
                    assertThat(ageRangeResponse.minAge()).isEqualTo(20);
                    assertThat(ageRangeResponse.maxAge()).isEqualTo(30);
                }
        );
        assertThat(meetingInfoResponses.meetings().get(0).title()).isEqualTo("다모여라");
        assertThat(meetingInfoResponses.meetings().get(0).status()).isEqualTo("WAITING");
        assertThat(meetingInfoResponses.meetings().get(0).meetingImage()).isEqualTo("https://glint.com/image.png");
        assertThat(meetingInfoResponses.meetings().get(0).locations()).containsExactly("서울");
        assertThat(meetingInfoResponses.meetings().get(0).peopleCapacity()).isEqualTo(4);


    }

}