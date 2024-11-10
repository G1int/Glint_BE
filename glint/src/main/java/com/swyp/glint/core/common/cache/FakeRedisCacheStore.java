package com.swyp.glint.core.common.cache;

import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Profile("local")
@Component
public class FakeRedisCacheStore implements CacheStore {
    private final Map<String, String> map;

    public FakeRedisCacheStore() {
        map = new ManagedMap<>();
    }

    @Override
    public String getData(String key) {
        return map.get(key);
    }

    @Override
    public void setData(String key, String value) {
        map.put(key, value);
    }

    @Override
    public void setDataExpire(String key, String value, long durationSeconds) {
        map.put(key, value);
        await(key, durationSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void setDataExpire(String key, String value, Duration expireDurationMinutes) {
        map.put(key, value);
        await(key, expireDurationMinutes.toMinutes(), TimeUnit.MINUTES);
    }

    @Override
    public void deleteData(String key) {
        map.remove(key);
    }

    public Long incData(String key) {
        return map.get(key) == null ? 1L : Long.parseLong(map.get(key)) + 1;
    }


    private void await(String key, long expireDuration, TimeUnit timeUnit) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
            try {
                timeUnit.sleep(expireDuration);
                map.remove(key);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
