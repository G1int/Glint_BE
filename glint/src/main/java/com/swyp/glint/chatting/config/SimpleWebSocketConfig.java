package com.swyp.glint.chatting.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@Profile("local")
@EnableWebSocketMessageBroker
public class SimpleWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    /**
     * 메시지 브로커 설정을 위한 메소드
     * /sub -> 메세지 구독(수신)
     * /pub -> 메세지 발행(송신)
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /* 메시지 앞에 해당 prefix로 해당 경로를 처리하고 있는 핸들러로 전달된다. */
        registry.setApplicationDestinationPrefixes("/pub");

        registry.enableSimpleBroker("/sub");
    }



    /**
     * 해당 엔드포인트로 요청 시 소켓 연결
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();

        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }


    /**
     * 클라이언트로부터 들어오는 요청을 처리하기 위한 채널 설정
     * 요청전 인터셉터를 설정 (e.g. Token 인증)
     * @param registration
     */
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler);
//    }
}