package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.SortedSetDataType;
import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;
import com.miniredis.miniredis.repository.CustomRedis;

public class ZAddCommand extends SortedSetCommand {

    @Override
    public Object executeCommand(String commandKey, String... keyAndValues)  throws WrongTypeException {
        String key = keyAndValues[0];
        String value = keyAndValues[1];
        String stScore = keyAndValues[2];
        int score = Integer.valueOf(stScore);

        SortedSetDataType sortedSet = super.getSortedSetData(key);

        if (sortedSet == null) {
            sortedSet = new SortedSetDataType();
        } else {
            if (sortedSet.getKeyToScore().containsKey(value)) {
                // Remove old key and old score
                sortedSet.getScoreToKey().remove(score, value);
            }
        }

        sortedSet.getKeyToScore().put(value, score);
        sortedSet.getScoreToKey().put(score, value);
        CustomRedis.getInstance().addKey(key, sortedSet);

        return 1;
    }


}
