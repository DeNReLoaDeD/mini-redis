package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.DataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.repository.CustomRedis;

public class DelCommand extends KeyCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues) throws WrongTypeException {
        DataType removedData = CustomRedis.getInstance().removeKey(keyAndValues[0]);
        if (removedData == null) return 0;
        return 1;
    }
}
