package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.DataType;
import com.miniredis.miniredis.domain.model.SetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.repository.CustomRedis;

import java.time.LocalDateTime;

public class GetCommand extends StringCommand {
    @Override
    public Object executeCommand(String commandKey, String... keyAndValues) throws WrongTypeException {

        DataType item = CustomRedis.getInstance().getKey(keyAndValues[0]);

        if(item == null) return null;

        if (!(item instanceof SetDataType)) {
            throw new WrongTypeException("WRONGTYPE Operation against a key holding the wrong kind of value");
        }
        SetDataType setItem = (SetDataType)item;

        if(setItem.getExpirationDate() != null &&
                LocalDateTime.now().isAfter(setItem.getExpirationDate())) {
            CustomRedis.getInstance().removeKey(keyAndValues[0]);
            return null;
        }
        return setItem.getValue();
    }
}
