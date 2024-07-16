package com.swyp.glint.system.config;

import com.swyp.glint.auth.filter.JwtLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
// @Controller에 @Secured 메소드를 사용하여 간단히 권한 체크를 할 수 있다. @Secured('ROLE_MANAGER')
// @PreAuthorize 어노테이션을 통해 권한점사 이전에 수행 여러 권한 허용할 때 @PreAuthorize("hasRole('ROLE_MANAGER')or haRole('ROLE_ADMIN')")
// @postAuthorize
//@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig  {

//    private final JwtLoginFilter jwtLoginFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        return http
                .csrf(AbstractHttpConfigurer::disable) //csrf 사용하지 않겠다.
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .httpBasic(AbstractHttpConfigurer::disable) //httpBasic 방식을 사용하지 않겠다.
                .formLogin(AbstractHttpConfigurer::disable) //formLogin 방식을 사용하지 않겠다.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                                authorize
//                                .requestMatchers("/manager/**").hasRole("ADMIN or MANAGER")
//                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                        .anyRequest().permitAll()
                )
                //UsernamePasswordAuthenticationFilter 필터 전에 jwtLoginFilter를 추가한다.
//                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilter(corsConfig.corsFilter())
                .build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(List.of("*")); //️ 허용할 origin
            config.setAllowCredentials(true);
            config.setAllowedOrigins(List.of("http://localhost:3000")); //️ 허용할 origin
            config.setExposedHeaders(List.of("*"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

            return config;
        };
    }
}

