package com.miniredis.miniredis.domain.service.command;

import com.miniredis.miniredis.domain.model.exceptions.WrongTypeException;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


public interface Command {
    Object executeCommand(String commandKey, String...keyAndValues) throws WrongTypeException;
}
