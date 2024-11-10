package com.swyp.glint.chatting.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.glint.chatting.application.dto.request.CreateChatMessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=local")
@SqlGroup({
        @Sql(value = "/sql/chatting/chat-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
})
public class ChatControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ChatController chatController;

    private ObjectMapper objectMapper;

    private WebSocketStompClient webSocketStompClient;

    @LocalServerPort
    private int port;


    @BeforeEach
    public void setUp() {
        webSocketStompClient = getStompClient();
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(chatController)
                .build();;
    }


    private WebSocketStompClient getStompClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport( new StandardWebSocketClient()))));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }

    @Test
    @DisplayName("미팅방에 채팅을 보낼 수 있다. profile이 local일 경우")
    public void simpleMessageQueueChatting() throws ExecutionException, InterruptedException, TimeoutException {
        // given
        Long chatroomId = 1L;

        StompSession session =
                webSocketStompClient
                .connectAsync("ws://localhost:" + port + "/glint/ws", new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        CompletableFuture<CreateChatMessageRequest> completableFuture = new CompletableFuture<>();

        session.subscribe("/sub/chatrooms/" + chatroomId, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return CreateChatMessageRequest.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((CreateChatMessageRequest) payload);
                System.out.println("payload = " + payload);
            }
        });

        // when
        CreateChatMessageRequest request = new CreateChatMessageRequest(1L, chatroomId, "Hello, STOMP!", "2021-08-01 00:00:00");
        session.send("/pub/chatrooms/" + chatroomId, request);

        // then
        CreateChatMessageRequest createChatMessageRequest = completableFuture.get(3, TimeUnit.SECONDS);
        assertThat(createChatMessageRequest.message()).isEqualTo("Hello, STOMP!");
    }

    @Test
    @DisplayName("미팅방 채팅을 조회할 수 있다.")
    public void getChatMessage() throws Exception {

        // given
        Long meetingId = 1L;

        // when
        // then
        mockMvc.perform(
                        get("/chatrooms/{meetingId}/chats", meetingId)
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.chats").isArray())
                .andExpect(jsonPath("$.chats[0].id").value(2L))
                .andExpect(jsonPath("$.chats[0].message").value("안녕하세요2"))
                .andExpect(jsonPath("$.chats[0].chatRoomId").value(1L))
                .andExpect(jsonPath("$.chats[0].userId").value(1L))
                .andExpect(jsonPath("$.chats[0].nickname").value("test"))
                .andExpect(jsonPath("$.chats[0].userProfileImageUrl").value("test.jpg"))
                .andExpect(jsonPath("$.chats[0].sendDate").value("2024-01-01 01:00:00"));
        ;
    }


    @Test
    @DisplayName("미팅방 채팅을 조회할 수 있다. ( no offset )")
    public void getNoOffsetChatMessage() throws Exception {

        // given
        Long meetingId = 1L;

        // when
        // then
        mockMvc.perform(
                        get("/chatrooms/{meetingId}/chats/no-offset", meetingId)
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.chats").isArray())
                .andExpect(jsonPath("$.chats[0].id").value(2L))
                .andExpect(jsonPath("$.chats[0].message").value("안녕하세요2"))
                .andExpect(jsonPath("$.chats[0].chatRoomId").value(1L))
                .andExpect(jsonPath("$.chats[0].userId").value(1L))
                .andExpect(jsonPath("$.chats[0].nickname").value("test"))
                .andExpect(jsonPath("$.chats[0].userProfileImageUrl").value("test.jpg"))
                .andExpect(jsonPath("$.chats[0].sendDate").value("2024-01-01 01:00:00"));
        ;
    }



    // todo : activeMQ 테스트 코드 작성
//    @Test
//    public void activeMQChatting() throws ExecutionException, InterruptedException, TimeoutException {
//        Long chatroomId = 1L;
//
//        StompSession session =
//                getStompClient()
//                        .connect("ws://localhost:" + port + "/glint/ws", new StompSessionHandlerAdapter() {})
//                        .get(1, TimeUnit.SECONDS);
//
//
//        CompletableFuture<CreateChatMessageRequest> completableFuture = new CompletableFuture<>();
//
//        session.subscribe("/sub/chatrooms/" + chatroomId, new StompFrameHandler() {
//            @Override
//            public Type getPayloadType(StompHeaders headers) {
//                return CreateChatMessageRequest.class;
//            }
//
//            @Override
//            public void handleFrame(StompHeaders headers, Object payload) {
//                completableFuture.complete((CreateChatMessageRequest) payload);
//                System.out.println("payload = " + payload);
//            }
//        });
//
//        CreateChatMessageRequest request = new CreateChatMessageRequest(1L, chatroomId, "Hello, STOMP!", "2021-08-01 00:00:00");
//
//        session.send("/pub/chatrooms/" + chatroomId, request);
//
//        CreateChatMessageRequest createChatMessageRequest = completableFuture.get(3, TimeUnit.SECONDS);
//        assertThat(createChatMessageRequest.message()).isEqualTo("Hello, STOMP!");
//
//    }

}
