package com.swyp.glint.system.config;


import com.swyp.glint.system.logtrace.LogTrace;
import com.swyp.glint.system.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
