package com.swyp.glint.chatting.presentation;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.chatting.application.dto.ChatRoomRequest;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/chatting/get-chat-room-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class ChatRoomControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ChatRoomController chatRoomController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(chatRoomController)
                .build();
    }

    @Test
    public void createChatRoom() throws Exception {

        mockMvc.perform(post("/meetings/{meetingId}/chatrooms", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ChatRoomRequest(List.of(1L, 2L))))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.chatRoomId").isNotEmpty())
                .andExpect(jsonPath("$.meetingId").value(1L))
                .andExpect(jsonPath("$.userIds").isArray())
                .andExpect(jsonPath("$.userIds[0]").value(1L))
                .andExpect(jsonPath("$.userIds[1]").value(2L))
                .andExpect(jsonPath("$.name").isEmpty())
                .andExpect(jsonPath("$.isActivated").value(false))
                .andExpect(jsonPath("$.isArchived").value(false))
        ;
    }



    @Test
    public void getChatRoom() throws Exception {

        mockMvc.perform(get("/meetings/{meetingId}/chatrooms", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatRoomId").isNotEmpty())
                .andExpect(jsonPath("$.meetingId").value(1L))
                .andExpect(jsonPath("$.userIds").isArray())
                .andExpect(jsonPath("$.userIds[0]").value(1L))
                .andExpect(jsonPath("$.userIds[1]").value(2L))
                .andExpect(jsonPath("$.name").value("chatRoom1"))
                .andExpect(jsonPath("$.isActivated").value(true))
                .andExpect(jsonPath("$.isArchived").value(false))
        ;
    }


}
