package com.azvtech.event_service.exception;

import com.azvtech.event_service.enums.Cause;
import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Fellipe Toledo
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        String errorMessage = ex.getMessage();

        log.debug("Received error message: {}", errorMessage);

        // Handle different enum types
        if (errorMessage != null) {
            if (errorMessage.startsWith("Invalid Cause:")) {
                handleEnumError(errors, "cause", errorMessage, "Invalid Cause:", Cause.values());
            }
            else if (errorMessage.startsWith("Invalid Status:")) {
                handleEnumError(errors, "status", errorMessage, "Invalid Status:", Status.values());
            }
            else if (errorMessage.startsWith("Invalid Severity:")) {
                handleEnumError(errors, "severity", errorMessage, "Invalid Severity:", Severity.values());
            }
            else {
                errors.put("error", errorMessage);
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );

        log.debug("Final response: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private void handleEnumError(
            Map<String, String> errors,
            String fieldName,
            String errorMessage,
            String prefix,
            Enum<?>[] enumValues) {

        String invalidValue = errorMessage.replace(prefix, "").trim();
        StringBuilder formattedError = new StringBuilder();
        formattedError.append("Invalid value '").append(invalidValue).append("'. ");
        formattedError.append("Allowed values are: [");

        // Get display names using reflection
        for (int i = 0; i < enumValues.length; i++) {
            try {
                Method getDisplayName = enumValues[i].getClass().getMethod("getDisplayName");
                String displayName = (String) getDisplayName.invoke(enumValues[i]);
                formattedError.append(displayName);

                if (i < enumValues.length - 1) {
                    formattedError.append(", ");
                }
            } catch (Exception e) {
                // Fallback to enum name if getDisplayName is not available
                formattedError.append(enumValues[i].name());

                if (i < enumValues.length - 1) {
                    formattedError.append(", ");
                }
            }
        }
        formattedError.append("]");

        log.debug("Formatted error for {}: {}", fieldName, formattedError);
        errors.put(fieldName, formattedError.toString());
    }

}
