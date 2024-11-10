package com.swyp.glint.chatting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Profile({"dev", "release"})
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Value("${activemq.stomp.host}")
    private String activemqStompHost;

    @Value("${activemq.stomp.port}")
    private String activemqStompPort;

    @Value("${activemq.user}")
    private String activemqUsername;

    @Value("${activemq.password}")
    private String activemqPassword;


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

        registry.enableStompBrokerRelay("/sub") // 메시지를 구독할 경로를 설정
                // ActiveMQ 브로커와 연결을 위한 호스트, 가상 호스트 및 포트, 관리자 로그인 설정
                .setRelayHost(activemqStompHost)
                .setRelayPort(Integer.parseInt(activemqStompPort))
                .setSystemLogin(activemqUsername)
                .setSystemPasscode(activemqPassword)
                .setClientLogin(activemqUsername)
                .setClientPasscode(activemqPassword);
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