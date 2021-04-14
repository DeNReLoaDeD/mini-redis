package com.miniredis.miniredis.domain.service;

import com.miniredis.miniredis.domain.IRedisService;
import com.miniredis.miniredis.domain.factory.CommandFactory;
import com.miniredis.miniredis.domain.model.CommandEnum;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class RedisService implements IRedisService {

    @Override
    public synchronized Object executeCommand(String command) {
        //decompose command and execute validations
        String[] decomposedCommand = decomposeCommand(command);
        String commandKey = decomposedCommand[0];
        String[] keyAndValues = Arrays.copyOfRange(decomposedCommand, 1, decomposedCommand.length);
        this.validateKeysAndValues(commandKey, keyAndValues);
        return CommandFactory.getCommand(commandKey).executeCommand(commandKey, keyAndValues);
    }

    private String[] decomposeCommand(String command){
        if(command == null || command.isEmpty()){
            throw new IllegalArgumentException("Insufficient arguments");
        }
        return command.split(" ");
    }

    private void validateKeysAndValues(String commandKey, String... keyAndValues) {
        if (CommandEnum.getEnumByCommand(commandKey, keyAndValues.length) == null) {
            //Doesn't exists a valid command for given parameters, throw exception
            throw new IllegalArgumentException("Invalid arguments for given command");
        }

    }
}



