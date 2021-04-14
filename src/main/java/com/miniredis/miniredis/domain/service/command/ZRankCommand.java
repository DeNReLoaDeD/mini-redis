package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.SortedSetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;

public class ZRankCommand extends SortedSetCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues)  throws WrongTypeException {
        SortedSetDataType sortedSet = super.getSortedSetData(keyAndValues[0]);
        if(sortedSet == null) return null;
        return sortedSet.getKeyToScore().get(keyAndValues[1]);
    }
}
