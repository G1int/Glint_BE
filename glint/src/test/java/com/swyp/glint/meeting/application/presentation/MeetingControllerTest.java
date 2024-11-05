package com.swyp.glint.meeting.application.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.core.system.error.ServiceExceptionHandler;
import com.swyp.glint.meeting.api.MeetingController;
import com.swyp.glint.meeting.application.dto.request.JoinConditionRequest;
import com.swyp.glint.meeting.application.dto.request.MeetingRequest;
import com.swyp.glint.meeting.application.dto.response.JoinConditionResponse;
import com.swyp.glint.user.application.dto.UserMeetingResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
