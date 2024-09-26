package com.swyp.glint.core.system.config;


import com.swyp.glint.core.system.logtrace.LogTrace;
import com.swyp.glint.core.system.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
