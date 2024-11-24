package com.fishingLog.spring.exception;

public class DataConversionException extends RuntimeException {
    public DataConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
