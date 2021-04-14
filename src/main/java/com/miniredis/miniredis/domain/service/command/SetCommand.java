package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.DataType;
import com.miniredis.miniredis.domain.model.SetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.repository.CustomRedis;

import java.time.LocalDateTime;

public class SetCommand extends StringCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues) throws WrongTypeException {
        SetDataType data = new SetDataType(keyAndValues[1]);

        if(keyAndValues.length == 4 && keyAndValues[2].equals("EX")){
            Integer expireInSeconds = 0;
            try {
                 expireInSeconds = Integer.valueOf(keyAndValues[3]);
            }
            catch(NumberFormatException e){
                throw new WrongTypeException("ERR value is not an integer or out of range");
            }
            //set expire
            LocalDateTime expirationDate = LocalDateTime.now();
            data.setExpirationDate(expirationDate.plusSeconds(expireInSeconds));
        }
        CustomRedis.getInstance().addKey(keyAndValues[0], data);
        return 1;
    }
}
