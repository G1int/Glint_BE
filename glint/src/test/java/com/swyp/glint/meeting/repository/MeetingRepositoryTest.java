package com.swyp.glint.meeting.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swyp.glint.meeting.application.dto.MeetingSearchCondition;
import com.swyp.glint.meeting.application.dto.response.MeetingInfoCountResponses;
import com.swyp.glint.meeting.domain.Meeting;
import com.swyp.glint.meeting.domain.MeetingDetail;
import com.swyp.glint.meeting.domain.MeetingInfo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(value = "/sql/user/meeting-repository-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class MeetingRepositoryTest {

    @Autowired
    private MeetingRepository meetingRepository;


    @Test
    public void findMeetingDetail() throws JsonProcessingException {
        // given
        Long meetingId = 1L;

        // when
        Optional<MeetingDetail> meetingDetailOptional = meetingRepository.findMeetingDetail(meetingId);


        // then
        assertThat(meetingDetailOptional).isPresent();
        MeetingDetail meetingDetail = meetingDetailOptional.get();
        assertThat(meetingDetail.getId()).isEqualTo(1L);
        assertThat(meetingDetail.getLeaderUserId()).isEqualTo(1L);
        assertThat(meetingDetail.getTitle()).isEqualTo("다모여라");
        assertThat(meetingDetail.getDescription()).isEqualTo("모두 모여모라");
        assertThat(meetingDetail.getUsers()).hasSize(1);
        assertThat(meetingDetail.getUsers().get(0)).satisfies(userSimpleProfile -> {
            assertThat(userSimpleProfile.getUserId()).isEqualTo(1L);
            assertThat(userSimpleProfile.getProfileImage()).isEqualTo("test.jpg");
            assertThat(userSimpleProfile.getNickname()).isEqualTo("test");
            assertThat(userSimpleProfile.getGender()).isEqualTo("MALE");
            assertThat(userSimpleProfile.getAge()).isEqualTo(34);
            assertThat(userSimpleProfile.getAffiliation()).isEqualTo("개발자");
        });

        assertThat(meetingDetail.getLocations().getLocationNames()).hasSize(1);
        assertThat(meetingDetail.getLocations().getLocationNames().get(0)).isEqualTo("서울 종로구");
        assertThat(meetingDetail.getMaleCondition().getSelectConditions()).hasSize(1);
        assertThat(meetingDetail.getMaleCondition().getSelectConditions().get(0)).isEqualTo("1");
        assertThat(meetingDetail.getMaleCondition().getAffiliation()).hasSize(2);
        assertThat(meetingDetail.getMaleCondition().getAffiliation().get(0)).isEqualTo("삼성전자");
        assertThat(meetingDetail.getMaleCondition().getAffiliation().get(1)).isEqualTo("서울대학교");
        assertThat(meetingDetail.getMaleCondition().getAgeRange().getMinAge()).isEqualTo(20);
        assertThat(meetingDetail.getMaleCondition().getAgeRange().getMaxAge()).isEqualTo(30);
        assertThat(meetingDetail.getMaleCondition().getHeightRange().getMinHeight()).isEqualTo(140);
        assertThat(meetingDetail.getMaleCondition().getHeightRange().getMaxHeight()).isEqualTo(200);
        assertThat(meetingDetail.getMaleCondition().getReligionConditions()).hasSize(1);
        assertThat(meetingDetail.getMaleCondition().getReligionConditions().get(0)).satisfies(religionCondition -> {
            assertThat(religionCondition.getId()).isEqualTo(1L);
            assertThat(religionCondition.getReligionName()).isEqualTo("무교");
        });
        assertThat(meetingDetail.getMaleCondition().getSmokingConditions()).hasSize(1);
        assertThat(meetingDetail.getMaleCondition().getSmokingConditions().get(0)).satisfies(smokingCondition -> {
            assertThat(smokingCondition.getId()).isEqualTo(1L);
            assertThat(smokingCondition.getSmokingName()).isEqualTo("비흡연");
        });
        assertThat(meetingDetail.getMaleCondition().getDrinkingConditions()).hasSize(1);
        assertThat(meetingDetail.getMaleCondition().getDrinkingConditions().get(0)).satisfies(drinkingCondition -> {
            assertThat(drinkingCondition.getId()).isEqualTo(1L);
            assertThat(drinkingCondition.getDrinkingName()).isEqualTo("마시지 않음");
        });


        assertThat(meetingDetail.getFemaleCondition().getSelectConditions()).hasSize(1);
        assertThat(meetingDetail.getFemaleCondition().getSelectConditions().get(0)).isEqualTo("1");
        assertThat(meetingDetail.getFemaleCondition().getAffiliation()).hasSize(2);
        assertThat(meetingDetail.getFemaleCondition().getAffiliation().get(0)).isEqualTo("삼성전자");
        assertThat(meetingDetail.getFemaleCondition().getAffiliation().get(1)).isEqualTo("서울대학교");
        assertThat(meetingDetail.getFemaleCondition().getAgeRange().getMinAge()).isEqualTo(20);
        assertThat(meetingDetail.getFemaleCondition().getAgeRange().getMaxAge()).isEqualTo(30);
        assertThat(meetingDetail.getFemaleCondition().getHeightRange().getMinHeight()).isEqualTo(140);
        assertThat(meetingDetail.getFemaleCondition().getHeightRange().getMaxHeight()).isEqualTo(200);
        assertThat(meetingDetail.getFemaleCondition().getReligionConditions()).hasSize(1);
        assertThat(meetingDetail.getFemaleCondition().getReligionConditions().get(0)).satisfies(religionCondition -> {
            assertThat(religionCondition.getId()).isEqualTo(1L);
            assertThat(religionCondition.getReligionName()).isEqualTo("무교");
        });
        assertThat(meetingDetail.getFemaleCondition().getSmokingConditions()).hasSize(1);
        assertThat(meetingDetail.getFemaleCondition().getSmokingConditions().get(0)).satisfies(smokingCondition -> {
            assertThat(smokingCondition.getId()).isEqualTo(1L);
            assertThat(smokingCondition.getSmokingName()).isEqualTo("비흡연");
        });
        assertThat(meetingDetail.getFemaleCondition().getDrinkingConditions()).hasSize(1);
        assertThat(meetingDetail.getFemaleCondition().getDrinkingConditions().get(0)).satisfies(drinkingCondition -> {
            assertThat(drinkingCondition.getId()).isEqualTo(1L);
            assertThat(drinkingCondition.getDrinkingName()).isEqualTo("마시지 않음");
        });
        assertThat(meetingDetail.getPeopleCapacity()).isEqualTo(4);
        assertThat(meetingDetail.getStatus()).isEqualTo("WAITING");
        assertThat(meetingDetail.getJoinRequestUserIds()).hasSize(1);
        assertThat(meetingDetail.getJoinRequestUserIds().get(0)).isEqualTo(1L);
        assertThat(meetingDetail.getLocationNames()).hasSize(1);
        assertThat(meetingDetail.getLocationNames().get(0)).isEqualTo("서울 종로구");
    }


    @Test
    @DisplayName("유저가 속해있는 종료되지 않은 미팅방들을 모두 조회할 수 있다")
    public void findAllNotEndMeetingByUserId() {
        // given
        Long userId = 1L;

        // when
        List<Meeting> meetings = meetingRepository.findAllNotEndMeetingByUserId(userId);

        // then
        assertThat(meetings).hasSize(1);
        assertThat(meetings.get(0).getId()).isEqualTo(1L);
        assertThat(meetings.get(0).getLeaderUserId()).isEqualTo(1L);
        assertThat(meetings.get(0).getTitle()).isEqualTo("다모여라");
        assertThat(meetings.get(0).getDescription()).isEqualTo("모두 모여모라");
        assertThat(meetings.get(0).getJoinUserIds()).hasSize(1);
        assertThat(meetings.get(0).getJoinUserIds().get(0)).isEqualTo(1L);
        assertThat(meetings.get(0).getLocationIds()).hasSize(1);
        assertThat(meetings.get(0).getLocationIds().get(0)).isEqualTo(1L);
        assertThat(meetings.get(0).getPeopleCapacity()).isEqualTo(4);
        assertThat(meetings.get(0).getStatus()).isEqualTo("WAITING");

    }


    @Test
    @DisplayName("모임 상태에 따라 모임 정보를 non-offset방식으로 조회할 수 있다")
    public void findAllMeetingInfoByStatus() {

        // given
        Long userId = 1L;
        String status = "WAITING";
        Long lastMeetingId = null;
        Integer limit = 1;

        // when
        List<MeetingInfo> meetingInfos = meetingRepository.findAllMeetingInfoByStatus(userId, status, lastMeetingId, limit);


        // then
        assertThat(meetingInfos).hasSize(1);
        assertThat(meetingInfos.get(0).getMeetingId()).isEqualTo(1L);
        assertThat(meetingInfos.get(0).getPeopleCapacity()).isEqualTo(4L);
        assertThat(meetingInfos.get(0).getLocationKeywords()).hasSize(1);
        assertThat(meetingInfos.get(0).getLocationKeywords().get(0)).isEqualTo("서울");
        assertThat(meetingInfos.get(0).getManAgeRange()).satisfies(ageRange -> {
            assertThat(ageRange.getMinAge()).isEqualTo(20);
            assertThat(ageRange.getMaxAge()).isEqualTo(30);
        });
        assertThat(meetingInfos.get(0).getWomanAgeRange()).satisfies(ageRange -> {
            assertThat(ageRange.getMinAge()).isEqualTo(20);
            assertThat(ageRange.getMaxAge()).isEqualTo(30);
        });
        assertThat(meetingInfos.get(0).getTitle()).isEqualTo("다모여라");
        assertThat(meetingInfos.get(0).getStatus()).isEqualTo("WAITING");
        assertThat(meetingInfos.get(0).getMeetingImage()).isNull();
        assertThat(meetingInfos.get(0).getMaleCount()).isEqualTo(1);
        assertThat(meetingInfos.get(0).getFemaleCount()).isEqualTo(0);

    }

    @Test
    @DisplayName("종료되지 않은 모든 미팅을 조회할 수 있다")
    public void findAllNotFinishMeeting() {

        // given
        Long lastId = null;
        Integer size = 1;

        // when
        List<MeetingInfo> meetingInfos = meetingRepository.findAllNotFinishMeeting(lastId, size);

        // then
        assertThat(meetingInfos).hasSize(1);
        assertThat(meetingInfos.get(0).getMeetingId()).isEqualTo(1L);
        assertThat(meetingInfos.get(0).getPeopleCapacity()).isEqualTo(4L);
        assertThat(meetingInfos.get(0).getLocationKeywords()).hasSize(1);
        assertThat(meetingInfos.get(0).getLocationKeywords().get(0)).isEqualTo("서울");
        assertThat(meetingInfos.get(0).getManAgeRange()).satisfies(ageRange -> {
            assertThat(ageRange.getMinAge()).isEqualTo(20);
            assertThat(ageRange.getMaxAge()).isEqualTo(30);
        });
        assertThat(meetingInfos.get(0).getWomanAgeRange()).satisfies(ageRange -> {
            assertThat(ageRange.getMinAge()).isEqualTo(20);
            assertThat(ageRange.getMaxAge()).isEqualTo(30);
        });
        assertThat(meetingInfos.get(0).getTitle()).isEqualTo("다모여라");
        assertThat(meetingInfos.get(0).getStatus()).isEqualTo("WAITING");
        assertThat(meetingInfos.get(0).getMeetingImage()).isNull();
        assertThat(meetingInfos.get(0).getMaleCount()).isEqualTo(1);
        assertThat(meetingInfos.get(0).getFemaleCount()).isEqualTo(0);

    }


    @Test
    @DisplayName("모임 검색조건으로 모임을 조회할 수 있다")
    public void searchMeetingWithTotalCount() {

        // given
        MeetingSearchCondition meetingSearchCondition = new MeetingSearchCondition(
                "다모여라",
                null,
                1,
                null
        );

        // when
        MeetingInfoCountResponses meetingInfoCountResponses = meetingRepository.searchMeetingWithTotalCount(meetingSearchCondition);

        // then
        assertThat(meetingInfoCountResponses.totalCount()).isEqualTo(1);
        assertThat(meetingInfoCountResponses.meetings().size()).isEqualTo(1);

    }


}
