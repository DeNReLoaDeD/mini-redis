package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.DataType;
import com.miniredis.miniredis.domain.model.SetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.repository.CustomRedis;

public class IncrCommand extends StringCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues) throws WrongTypeException{
        DataType item = CustomRedis.getInstance().getKey(keyAndValues[0]);
        if (item != null) {
            if (!(item instanceof SetDataType)) {
                throw new WrongTypeException("WRONGTYPE Operation against a key holding the wrong kind of value");
            }

            SetDataType setItem = (SetDataType) item;

            if (setItem.getValue() instanceof Integer) {
                setItem.setValue((Integer) setItem.getValue() + 1);
                CustomRedis.getInstance().addKey(keyAndValues[0], setItem);
                return setItem.getValue();
            } else {
                throw new WrongTypeException("ERR value is not an integer or out of range");
            }

        } else {
            item = new SetDataType(1);
            CustomRedis.getInstance().addKey(keyAndValues[0], item);
            return 1;
        }
    }
}
