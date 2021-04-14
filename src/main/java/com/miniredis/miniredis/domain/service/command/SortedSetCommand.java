package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.DataType;
import com.miniredis.miniredis.domain.model.SortedSetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.domain.service.command.Command;
import com.miniredis.miniredis.repository.CustomRedis;

public abstract class SortedSetCommand implements Command {

    SortedSetDataType getSortedSetData(String key) {
        DataType data = CustomRedis.getInstance().getKey(key);

        if (data == null) return null;

        if (!(data instanceof SortedSetDataType)) {
            throw new WrongTypeException("Key is not a sorted set");
        }
        return (SortedSetDataType) data;
    }
}
