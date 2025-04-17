package com.azvtech.event_service.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException() {
        super("Requested resource not found");
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
}
