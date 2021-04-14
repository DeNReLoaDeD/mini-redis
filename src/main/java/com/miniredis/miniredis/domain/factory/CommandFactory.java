package com.miniredis.miniredis.domain.factory;

import com.miniredis.miniredis.domain.service.command.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<String, Supplier<Command>> registeredCommands = new HashMap<>();

    static {
        registeredCommands.put("get", GetCommand::new);
        registeredCommands.put("set", SetCommand::new);
        registeredCommands.put("del", DelCommand::new);
        registeredCommands.put("incr", IncrCommand::new);
        registeredCommands.put("dbsize", DBSizeCommand::new);
        registeredCommands.put("zadd", ZAddCommand::new);
        registeredCommands.put("zcard", ZCardCommand::new);
        registeredCommands.put("zrank",ZRankCommand::new);
        registeredCommands.put("zrange", ZRangeCommand::new);
    }

    public static Command getCommand(String command) {
        Supplier<Command> com = registeredCommands.get(command.toLowerCase());
        return com.get();
    }

}
