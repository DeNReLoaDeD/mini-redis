package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.repository.CustomRedis;

public class DBSizeCommand extends ServerCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues) throws WrongTypeException {
        return CustomRedis.getInstance().keysLength();
    }
}
