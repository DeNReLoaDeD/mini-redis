package com.miniredis.miniredis.domain;

public interface IRedisService {
    Object executeCommand(String command);
}
