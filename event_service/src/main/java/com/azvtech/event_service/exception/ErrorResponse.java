package com.azvtech.event_service.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Fellipe Toledo
 */
@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                ", timestamp=" + timestamp +
                '}';
    }
}
