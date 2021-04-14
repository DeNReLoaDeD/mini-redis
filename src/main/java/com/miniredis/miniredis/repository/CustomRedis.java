package com.miniredis.miniredis.repository;

import com.miniredis.miniredis.domain.model.DataType;

import java.util.HashMap;
import java.util.Map;

public class CustomRedis {

    private static CustomRedis instance;
    private static Map<String, DataType> keys;

    private CustomRedis() {
        keys = new HashMap<>();
    }

    public static CustomRedis getInstance() {
        if (instance == null) {
            instance = new CustomRedis();
        }
        return instance;
    }

    public void addKey(String key, DataType obj) {
        this.keys.put(key, obj);
    }

    public DataType removeKey(String key) {
        return this.keys.remove(key);
    }

    public DataType getKey(String key) {
        return this.keys.get(key);
    }

    public int keysLength() {
        return this.keys.size();
    }

}
