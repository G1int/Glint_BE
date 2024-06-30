package com.swyp.glint.common.util;

import com.swyp.glint.common.cache.RemoteCache;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


import java.time.Duration;

@Getter
@Component
@RequiredArgsConstructor
public class RedisUtil implements RemoteCache {

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void setData(String key, String value) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofDays(1);
        valueOperations.set(key, value, expireDuration);
    }

    @Override
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    @Override
    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }



    @Override
    public Long incData(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }


}
