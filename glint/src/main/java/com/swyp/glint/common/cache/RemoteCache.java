package com.swyp.glint.common.cache;

public interface RemoteCache {
    String getData(String key);

    void setData(String key, String value);

    void setDataExpire(String key, String value, long duration);

    void deleteData(String key);


    Long incData(String key);
}
