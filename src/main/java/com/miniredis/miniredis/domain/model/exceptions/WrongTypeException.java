package com.miniredis.miniredis.domain.model.exceptions;

public class WrongTypeException extends RuntimeException{
    public WrongTypeException(String errorMessage) {
        super(errorMessage);
    }
}
