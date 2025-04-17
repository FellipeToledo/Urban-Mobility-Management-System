package com.azvtech.event_service.exception;

import com.azvtech.event_service.enums.Cause;
import com.azvtech.event_service.enums.Severity;
import com.azvtech.event_service.enums.Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fellipe Toledo
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Tratamento para validação de DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation Error",
                        "Invalid request content",
                        request.getRequestURI(),
                        errors
                )
        );
    }

    // Tratamento para enums inválidos
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        Map<String, String> details = new HashMap<>();
        String message = ex.getMessage();

        if (message != null) {
            if (message.startsWith("Invalid Cause:")) {
                details.put("cause", buildEnumErrorMessage(message, "Invalid Cause:", Cause.values()));
            } else if (message.startsWith("Invalid Status:")) {
                details.put("status", buildEnumErrorMessage(message, "Invalid Status:", Status.values()));
            } else if (message.startsWith("Invalid Severity:")) {
                details.put("severity", buildEnumErrorMessage(message, "Invalid Severity:", Severity.values()));
            } else {
                details.put("error", message);
            }
        }

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid Input",
                        "Invalid enum value provided",
                        request.getRequestURI(),
                        details
                )
        );
    }

    // Tratamento para entidade não encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Tratamento genérico para erros internos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Internal error: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(
                new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Error",
                        "An unexpected error occurred",
                        request.getRequestURI()
                )
        );
    }

    // Método auxiliar para mensagens de enum
    private String buildEnumErrorMessage(String message, String prefix, Enum<?>[] values) {
        String invalidValue = message.replace(prefix, "").trim();
        return String.format(
                "Invalid value '%s'. Allowed values: %s",
                invalidValue,
                String.join(", ", EnumUtils.getNames(values))
        );
    }

    @ExceptionHandler(EventTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleEventTypeMismatch(
            EventTypeMismatchException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Type Mismatch",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
