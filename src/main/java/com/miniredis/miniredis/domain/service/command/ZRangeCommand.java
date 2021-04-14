package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.SortedSetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;

import java.util.ArrayList;
import java.util.SortedMap;

public class ZRangeCommand extends SortedSetCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues) throws WrongTypeException {
        String key = keyAndValues[0];
        String start = keyAndValues[1];
        String end = keyAndValues[2];

        SortedSetDataType sortedSet = super.getSortedSetData(key);
        if(sortedSet != null) {
            SortedMap<Integer, String> submap =
                    sortedSet.getScoreToKey().subMap(Integer.valueOf(start), Integer.valueOf(end) + 1);
            return new ArrayList(submap.values());
        }
        return null;
    }
}
