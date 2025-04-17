package com.azvtech.event_service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Fellipe Toledo
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private String path;
    private Map<String, String> details;
    private LocalDateTime timestamp;

    // Construtores para diferentes cenários
    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, String path, Map<String, String> details) {
        this(status, error, message, path);
        this.details = details;
    }
}
