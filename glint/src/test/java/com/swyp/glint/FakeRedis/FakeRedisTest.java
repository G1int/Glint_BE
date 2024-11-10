package com.swyp.glint.FakeRedis;

import com.swyp.glint.core.common.cache.FakeRedisCacheStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

public class FakeRedisTest {

    private FakeRedisCacheStore cacheStore;

    @BeforeEach
    public void setUp() {
        cacheStore = new FakeRedisCacheStore();
    }

    @Test
    @DisplayName("데이터 저장 및 조회 할 수 있다.")
    public void setData() {
        // given
        String key = "name";
        String value = "tom";

        // when
        cacheStore.setData(key, value);

        // then
        String data = cacheStore.getData(key);

        assertThat(data).isEqualTo(value);
    }

    @Test
    @DisplayName("지정한 시간이 지나면 데이터가 삭제된다.")
    public void timeExpireDateSecond() throws InterruptedException {
        // given
        String key = "name";
        String value = "tom";
        long durationOfSecond = 5;

        // when
        cacheStore.setDataExpire(key, value, durationOfSecond);
        Thread.sleep(Duration.ofSeconds(durationOfSecond).toMillis());

        // then
        String data = cacheStore.getData(key);

        assertThat(data).isNull();
    }

    @Test
    @DisplayName("지정한 시간이 지나면 데이터가 삭제된다.")
    public void timeExpireDateMinutes() throws InterruptedException {
        // given
        String key = "name";
        String value = "tom";
        Duration durationOfMinutes = Duration.ofMinutes(1);

        // when
        cacheStore.setDataExpire(key, value, durationOfMinutes);
        TimeUnit.of(SECONDS).sleep(durationOfMinutes.toSeconds() + 10);

        // then
        String data = cacheStore.getData(key);
        assertThat(data).isNull();
    }

}
