package com.azvtech.event_service.exception;

import com.azvtech.event_service.enums.Cause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if (errorMessage != null && errorMessage.startsWith("Invalid Cause:")) {
            String invalidValue = errorMessage.replace("Invalid Cause:", "").trim();

            StringBuilder formattedError = new StringBuilder();
            formattedError.append("Invalid value '").append(invalidValue).append("'. ");
            formattedError.append("Allowed values are: [");

            Cause[] causes = Cause.values();
            for (int i = 0; i < causes.length; i++) {
                formattedError.append(causes[i].getDisplayName());
                if (i < causes.length - 1) {
                    formattedError.append(", ");
                }
            }
            formattedError.append("]");

            log.debug("Formatted error: {}", formattedError.toString());
            errors.put("cause", formattedError.toString());
        } else {
            errors.put("error", errorMessage);
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );

        log.debug("Final response: {}", errorResponse);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
