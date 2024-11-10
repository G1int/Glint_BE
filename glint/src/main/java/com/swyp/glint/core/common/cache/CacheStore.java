package com.swyp.glint.core.common.cache;

import java.time.Duration;

public interface CacheStore {
    String getData(String key);

    void setData(String key, String value);

    void setDataExpire(String key, String value, long duration);

    void setDataExpire(String key, String value, Duration expireDuration);

    void deleteData(String key);

}
