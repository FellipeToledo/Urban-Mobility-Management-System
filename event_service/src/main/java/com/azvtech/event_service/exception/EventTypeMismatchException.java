package com.azvtech.event_service.exception;

public class EventTypeMismatchException extends RuntimeException {
    public EventTypeMismatchException(String message) {
        super(message);
    }
}
