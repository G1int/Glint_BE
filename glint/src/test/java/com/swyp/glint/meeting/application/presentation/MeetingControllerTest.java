package com.swyp.glint.meeting.application.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.core.system.error.ServiceExceptionHandler;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.meeting.api.MeetingController;
import com.swyp.glint.meeting.application.dto.request.JoinConditionRequest;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.user.application.dto.ReligionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(value = "/sql/meeting/meeting-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
@SpringBootTest
public class MeetingControllerTest {

    @Autowired
    private MeetingController meetingController;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;


    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(meetingController)
                .setControllerAdvice(ServiceExceptionHandler.class)
                .build();
    }

    @Test
    @DisplayName("미팅 생성")
    public void createMeeting() throws Exception {
        // given

        MeetingRequest meetingRequest = MeetingRequest.builder()
                .title("미팅")
                .description("미팅 설명")
                .leaderUserId(1L)
                .locationIds(List.of(1L))
                .maleConditions(
                        JoinConditionRequest.builder()
                                .selectConditions(List.of("AGE", "HEIGHT"))
                                .affiliation(List.of("삼성전자"))
                                .maxAge(40)
                                .minAge(25)
                                .maxHeight(200)
                                .minHeight(160)
                                .religionIds(List.of())
                                .smokingIds(List.of())
                                .drinkingIds(List.of())
                                .build()
                )
                .femaleConditions(
                        JoinConditionRequest.builder()
                                .selectConditions(List.of("AGE", "HEIGHT"))
                                .affiliation(List.of("삼성전자"))
                                .maxAge(35)
                                .minAge(25)
                                .maxHeight(180)
                                .minHeight(140)
                                .religionIds(List.of())
                                .smokingIds(List.of())
                                .drinkingIds(List.of())
                                .build()
                )
                .peopleCapacity("4")
                .build();

        // when
        // then
        mockMvc.perform(post("/meeting")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.leaderUserId").value(1L))
                .andExpect(jsonPath("$.title").value("미팅"))
                .andExpect(jsonPath("$.description").value("미팅 설명"))
                .andExpect(jsonPath("$.users[0].id").value(1L))
                .andExpect(jsonPath("$.users[0].profileImage").value("test.jpg"))
                .andExpect(jsonPath("$.users[0].nickname").value("test"))
                .andExpect(jsonPath("$.users[0].gender").value("MALE"))
                .andExpect(jsonPath("$.users[0].age").value(29))
                .andExpect(jsonPath("$.users[0].affiliation").value("삼성전자"))
                .andExpect(jsonPath("$.locations[0]").value("서울 종로구"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.maleCondition.affiliation[0]").value("삼성전자"))
                .andExpect(jsonPath("$.maleCondition.minAge").value(25))
                .andExpect(jsonPath("$.maleCondition.maxAge").value(40))
                .andExpect(jsonPath("$.maleCondition.minHeight").value(160))
                .andExpect(jsonPath("$.maleCondition.maxHeight").value(200))
                .andExpect(jsonPath("$.maleCondition.religion").isEmpty())
                .andExpect(jsonPath("$.maleCondition.smoking").isEmpty())
                .andExpect(jsonPath("$.maleCondition.drinking").isEmpty())
                .andExpect(jsonPath("$.femaleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.femaleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.femaleCondition.affiliation[0]").value("삼성전자"))
                .andExpect(jsonPath("$.femaleCondition.minAge").value(25))
                .andExpect(jsonPath("$.femaleCondition.maxAge").value(35))
                .andExpect(jsonPath("$.femaleCondition.minHeight").value(140))
                .andExpect(jsonPath("$.femaleCondition.maxHeight").value(180))
                .andExpect(jsonPath("$.femaleCondition.religion").isEmpty())
                .andExpect(jsonPath("$.femaleCondition.smoking").isEmpty())
                .andExpect(jsonPath("$.femaleCondition.drinking").isEmpty())
                .andExpect(jsonPath("$.peopleCapacity").value(4))
                .andExpect(jsonPath("$.status").value("WAITING"))
                .andExpect(jsonPath("$.joinRequestUserIds").isEmpty())
        ;
    }


    @Test
    @DisplayName("미팅 수정")
    public void updateMeeting() throws Exception {
        // given
        MeetingRequest meetingRequest = MeetingRequest.builder()
                .title("미팅")
                .description("미팅 설명")
                .leaderUserId(1L)
                .locationIds(List.of(1L))
                .maleConditions(
                        JoinConditionRequest.builder()
                                .selectConditions(List.of("AGE", "HEIGHT"))
                                .affiliation(List.of("삼성전자"))
                                .maxAge(40)
                                .minAge(25)
                                .maxHeight(200)
                                .minHeight(160)
                                .religionIds(List.of())
                                .smokingIds(List.of())
                                .drinkingIds(List.of())
                                .build()
                )
                .femaleConditions(
                        JoinConditionRequest.builder()
                                .selectConditions(List.of("AGE", "HEIGHT"))
                                .affiliation(List.of("삼성전자"))
                                .maxAge(35)
                                .minAge(25)
                                .maxHeight(180)
                                .minHeight(140)
                                .religionIds(List.of())
                                .smokingIds(List.of())
                                .drinkingIds(List.of())
                                .build()
                )
                .peopleCapacity("4")
                .build();

        // when
        // then
        mockMvc.perform(put("/meetings/{meetingId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meetingRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.leaderUserId").value(1L))
                .andExpect(jsonPath("$.title").value("미팅"))
                .andExpect(jsonPath("$.description").value("미팅 설명"))
                .andExpect(jsonPath("$.users[0].id").value(1L))
                .andExpect(jsonPath("$.users[0].profileImage").value("test.jpg"))
                .andExpect(jsonPath("$.users[0].nickname").value("test"))
                .andExpect(jsonPath("$.users[0].gender").value("MALE"))
                .andExpect(jsonPath("$.users[0].age").value(29))
                .andExpect(jsonPath("$.users[0].affiliation").value("삼성전자"))
                .andExpect(jsonPath("$.locations[0]").value("서울 종로구"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.maleCondition.affiliation[0]").value("삼성전자"))
                .andExpect(jsonPath("$.maleCondition.minAge").value(25))
                .andExpect(jsonPath("$.maleCondition.maxAge").value(40))
                .andExpect(jsonPath("$.maleCondition.minHeight").value(160))
                .andExpect(jsonPath("$.maleCondition.maxHeight").value(200))
                .andExpect(jsonPath("$.maleCondition.religion").isEmpty())
                .andExpect(jsonPath("$.maleCondition.smoking").isEmpty())
                .andExpect(jsonPath("$.maleCondition.drinking").isEmpty())
                .andExpect(jsonPath("$.femaleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.femaleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.femaleCondition.affiliation[0]").value("삼성전자"))
                .andExpect(jsonPath("$.femaleCondition.minAge").value(25))
                .andExpect(jsonPath("$.femaleCondition.maxAge").value(35))
                .andExpect(jsonPath("$.femaleCondition.minHeight").value(140))
                .andExpect(jsonPath("$.femaleCondition.maxHeight").value(180))
                .andExpect(jsonPath("$.femaleCondition.religion").isEmpty())
                .andExpect(jsonPath("$.femaleCondition.smoking").isEmpty())
                .andExpect(jsonPath("$.femaleCondition.drinking").isEmpty())
                .andExpect(jsonPath("$.peopleCapacity").value(4))
                .andExpect(jsonPath("$.status").value("WAITING"))
                .andExpect(jsonPath("$.joinRequestUserIds[0]").value(1))
                .andExpect(jsonPath("$.joinRequestUserIds[1]").value(2))
                .andExpect(jsonPath("$.joinRequestUserIds[2]").value(3))

        ;

    }


    @Test
    @DisplayName("미팅 조회")
    public void getMeeting() throws Exception {
        // given
        Long meetingId = 1L;

        // when
        // then
        mockMvc.perform(get("/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.leaderUserId").value(1L))
                .andExpect(jsonPath("$.title").value("다모여라"))
                .andExpect(jsonPath("$.description").value("모두 모여라아"))
                .andExpect(jsonPath("$.users[0].id").value(1L))
                .andExpect(jsonPath("$.users[0].profileImage").value("test.jpg"))
                .andExpect(jsonPath("$.users[0].nickname").value("test"))
                .andExpect(jsonPath("$.users[0].gender").value("MALE"))
                .andExpect(jsonPath("$.users[0].age").value(29))
                .andExpect(jsonPath("$.users[0].affiliation").value("삼성전자"))
                .andExpect(jsonPath("$.locations[0]").value("서울 종로구"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.maleCondition.affiliation[0]").value("삼성전자"))
                .andExpect(jsonPath("$.maleCondition.minAge").value(20))
                .andExpect(jsonPath("$.maleCondition.maxAge").value(30))
                .andExpect(jsonPath("$.maleCondition.minHeight").value(140))
                .andExpect(jsonPath("$.maleCondition.maxHeight").value(200))
                .andExpect(jsonPath("$.maleCondition.religion[0].religionId").value(1))
                .andExpect(jsonPath("$.maleCondition.religion[0].religionName").value("무교"))
                .andExpect(jsonPath("$.maleCondition.smoking[0].smokingId").value(1))
                .andExpect(jsonPath("$.maleCondition.smoking[0].smokingName").value("비흡연"))
                .andExpect(jsonPath("$.maleCondition.drinking[0].drinkingId").value(1))
                .andExpect(jsonPath("$.maleCondition.drinking[0].drinkingName").value("마시지 않음"))
                .andExpect(jsonPath("$.femaleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.femaleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.femaleCondition.affiliation[0]").value("서울대학교"))
                .andExpect(jsonPath("$.femaleCondition.minAge").value(20))
                .andExpect(jsonPath("$.femaleCondition.maxAge").value(30))
                .andExpect(jsonPath("$.femaleCondition.minHeight").value(140))
                .andExpect(jsonPath("$.femaleCondition.maxHeight").value(200))
                .andExpect(jsonPath("$.femaleCondition.religion[0].religionId").value(1))
                .andExpect(jsonPath("$.femaleCondition.religion[0].religionName").value("무교"))
                .andExpect(jsonPath("$.femaleCondition.smoking[0].smokingId").value(1))
                .andExpect(jsonPath("$.femaleCondition.smoking[0].smokingName").value("비흡연"))
                .andExpect(jsonPath("$.femaleCondition.drinking[0].drinkingId").value(1))
                .andExpect(jsonPath("$.femaleCondition.drinking[0].drinkingName").value("마시지 않음"))
                .andExpect(jsonPath("$.peopleCapacity").value(4))
                .andExpect(jsonPath("$.status").value("WAITING"))
                .andExpect(jsonPath("$.joinRequestUserIds[0]").value(1))
                .andExpect(jsonPath("$.joinRequestUserIds[1]").value(2))
                .andExpect(jsonPath("$.joinRequestUserIds[2]").value(3))
        ;
    }



    @Test
    @DisplayName("New 미팅 조회")
    public void getNewMeeting() throws Exception {
        // given
        Long meetingId = 1L;

        // when
        // then
        mockMvc.perform(get("/meetings/new", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meetings[0].meetingId").value(1))
                .andExpect(jsonPath("$.meetings[0].maleCount").value(1))
                .andExpect(jsonPath("$.meetings[0].femaleCount").value(0))
                .andExpect(jsonPath("$.meetings[0].maleAgeRange.minAge").value(20))
                .andExpect(jsonPath("$.meetings[0].maleAgeRange.maxAge").value(30))
                .andExpect(jsonPath("$.meetings[0].femaleAgeRange.minAge").value(20))
                .andExpect(jsonPath("$.meetings[0].femaleAgeRange.maxAge").value(30))
                .andExpect(jsonPath("$.meetings[0].title").value("다모여라"))
                .andExpect(jsonPath("$.meetings[0].status").value("WAITING"))
                .andExpect(jsonPath("$.meetings[0].meetingImage").value("https://glint.com/image.png"))
                .andExpect(jsonPath("$.meetings[0].locations[0]").value("서울"))
                .andExpect(jsonPath("$.meetings[0].peopleCapacity").value(4))
        ;
    }


    @Test
    @DisplayName("내가 속한 미팅 조회")
    public void getMyMeeting() throws Exception {
        // given
        Long userId = 1L;

        // when
        // then
        mockMvc.perform(get("/meetings/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("status", "WAITING")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meetings[0].meetingId").value(1))
                .andExpect(jsonPath("$.meetings[0].maleCount").value(1))
                .andExpect(jsonPath("$.meetings[0].femaleCount").value(0))
                .andExpect(jsonPath("$.meetings[0].maleAgeRange.minAge").value(20))
                .andExpect(jsonPath("$.meetings[0].maleAgeRange.maxAge").value(30))
                .andExpect(jsonPath("$.meetings[0].femaleAgeRange.minAge").value(20))
                .andExpect(jsonPath("$.meetings[0].femaleAgeRange.maxAge").value(30))
                .andExpect(jsonPath("$.meetings[0].title").value("다모여라"))
                .andExpect(jsonPath("$.meetings[0].status").value("WAITING"))
                .andExpect(jsonPath("$.meetings[0].meetingImage").value("https://glint.com/image.png"))
                .andExpect(jsonPath("$.meetings[0].locations[0]").value("서울"))
                .andExpect(jsonPath("$.meetings[0].peopleCapacity").value(4))
        ;
    }


    @Test
    @DisplayName("미팅 나가기")
    public void userOutMeeting() throws Exception {
        // given
        Long userId = 2L;
        Long meetingId = 1L;

        // when
        // then
        mockMvc.perform(put("/meetings/{meetingId}/users/{userId}/out", meetingId, userId)
                        .contentType(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.leaderUserId").value(1L))
                .andExpect(jsonPath("$.title").value("다모여라"))
                .andExpect(jsonPath("$.description").value("모두 모여라아"))
                .andExpect(jsonPath("$.users[0].id").value(1L))
                .andExpect(jsonPath("$.users[0].profileImage").value("test.jpg"))
                .andExpect(jsonPath("$.users[0].nickname").value("test"))
                .andExpect(jsonPath("$.users[0].gender").value("MALE"))
                .andExpect(jsonPath("$.users[0].age").value(29))
                .andExpect(jsonPath("$.users[0].affiliation").value("삼성전자"))
                .andExpect(jsonPath("$.locations[0]").value("서울 종로구"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.maleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.maleCondition.affiliation[0]").value("삼성전자"))
                .andExpect(jsonPath("$.maleCondition.minAge").value(20))
                .andExpect(jsonPath("$.maleCondition.maxAge").value(30))
                .andExpect(jsonPath("$.maleCondition.minHeight").value(140))
                .andExpect(jsonPath("$.maleCondition.maxHeight").value(200))
                .andExpect(jsonPath("$.maleCondition.religion[0].religionId").value(1))
                .andExpect(jsonPath("$.maleCondition.religion[0].religionName").value("무교"))
                .andExpect(jsonPath("$.maleCondition.smoking[0].smokingId").value(1))
                .andExpect(jsonPath("$.maleCondition.smoking[0].smokingName").value("비흡연"))
                .andExpect(jsonPath("$.maleCondition.drinking[0].drinkingId").value(1))
                .andExpect(jsonPath("$.maleCondition.drinking[0].drinkingName").value("마시지 않음"))
                .andExpect(jsonPath("$.femaleCondition.selectConditions[0]").value("AGE"))
                .andExpect(jsonPath("$.femaleCondition.selectConditions[1]").value("HEIGHT"))
                .andExpect(jsonPath("$.femaleCondition.affiliation[0]").value("서울대학교"))
                .andExpect(jsonPath("$.femaleCondition.minAge").value(20))
                .andExpect(jsonPath("$.femaleCondition.maxAge").value(30))
                .andExpect(jsonPath("$.femaleCondition.minHeight").value(140))
                .andExpect(jsonPath("$.femaleCondition.maxHeight").value(200))
                .andExpect(jsonPath("$.femaleCondition.religion[0].religionId").value(1))
                .andExpect(jsonPath("$.femaleCondition.religion[0].religionName").value("무교"))
                .andExpect(jsonPath("$.femaleCondition.smoking[0].smokingId").value(1))
                .andExpect(jsonPath("$.femaleCondition.smoking[0].smokingName").value("비흡연"))
                .andExpect(jsonPath("$.femaleCondition.drinking[0].drinkingId").value(1))
                .andExpect(jsonPath("$.femaleCondition.drinking[0].drinkingName").value("마시지 않음"))
                .andExpect(jsonPath("$.peopleCapacity").value(4))
                .andExpect(jsonPath("$.status").value("WAITING"))
                .andExpect(jsonPath("$.joinRequestUserIds[0]").value(1))
                .andExpect(jsonPath("$.joinRequestUserIds[1]").value(2))
                .andExpect(jsonPath("$.joinRequestUserIds[2]").value(3))
        ;
    }


    @Test
    @DisplayName("미팅 검색")
    public void searchMeeting() throws Exception {
        // given
        Long userId = 1L;

        // when
        // then
        mockMvc.perform(get("/meetings/search", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("keyword", "모여")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meetings[0].meetingId").value(1))
                .andExpect(jsonPath("$.meetings[0].maleCount").value(1))
                .andExpect(jsonPath("$.meetings[0].femaleCount").value(0))
                .andExpect(jsonPath("$.meetings[0].maleAgeRange.minAge").value(20))
                .andExpect(jsonPath("$.meetings[0].maleAgeRange.maxAge").value(30))
                .andExpect(jsonPath("$.meetings[0].femaleAgeRange.minAge").value(20))
                .andExpect(jsonPath("$.meetings[0].femaleAgeRange.maxAge").value(30))
                .andExpect(jsonPath("$.meetings[0].title").value("다모여라"))
                .andExpect(jsonPath("$.meetings[0].status").value("WAITING"))
                .andExpect(jsonPath("$.meetings[0].meetingImage").value("https://glint.com/image.png"))
                .andExpect(jsonPath("$.meetings[0].locations[0]").value("서울"))
                .andExpect(jsonPath("$.meetings[0].peopleCapacity").value(4))
                .andExpect(jsonPath("$.totalCount").value(1))
        ;
    }

}
