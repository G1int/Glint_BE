package com.swyp.glint.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

public class CorsConfig {
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        // 내 서버가 응답을 할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
        config.setAllowCredentials(true);
        // 모든 ip에 응답을 허용
        config.addAllowedOrigin("*");
        // 모든 Header에 응답을 허용
        config.addAllowedHeader("*");
        // 모든 post,get,put,delete,patch 요청을 허용
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/glint/**",config);

        return new CorsFilter(source);
    }
}
