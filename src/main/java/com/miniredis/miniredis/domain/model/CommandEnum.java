package com.miniredis.miniredis.domain.model;

public enum CommandEnum {
    SET( "set",  2,4),
    GET( "get",  1,1),
    DEL( "del",  1,1),
    INCR( "incr",  1,1),
    DBSIZE( "dbsize",  0,0),
    ZADD( "zadd",  3,3),
    ZRANK( "zrank",  2,2),
    ZCARD( "zcard",  1,1),
    ZRANGE( "zrange",  3,3);


    private String command;
    private int minArguments;
    private int maxArguments;

    CommandEnum(String command, int minArguments, int maxArguments){
        this.command = command;
        this.minArguments = minArguments;
        this.maxArguments = maxArguments;
    }

    public static CommandEnum getEnumByCommand(String command, int argumentNumber) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.command.equals(command.toLowerCase())
                    && (argumentNumber >= commandEnum.minArguments && argumentNumber <= commandEnum.maxArguments)) {
                return commandEnum;
            }
        }
        return null;
    }
}
