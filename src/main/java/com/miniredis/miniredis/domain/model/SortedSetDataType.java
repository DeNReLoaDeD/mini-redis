package com.miniredis.miniredis.domain.model;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class SortedSetDataType extends DataType {

    private SortedMap<String, Integer> keyToScore;
    private SortedMap<Integer, String> scoreToKey;

    public SortedSetDataType() {
        keyToScore = Collections.synchronizedSortedMap(new TreeMap<>());
        scoreToKey = Collections.synchronizedSortedMap(new TreeMap<>());
    }

    public SortedMap<String, Integer> getKeyToScore() {
        return keyToScore;
    }

    public SortedMap<Integer, String> getScoreToKey() {
        return scoreToKey;
    }

}
