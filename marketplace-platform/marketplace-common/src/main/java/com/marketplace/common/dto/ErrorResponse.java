package com.marketplace.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;

/**
 * Standard error response DTO following RFC 7807 (Problem Details) pattern.
 * Used by GlobalExceptionHandler for consistent API error responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String code,
    String message,
    String path,
    List<FieldError> fieldErrors
) {

    public static ErrorResponse of(int status, String error, String code, String message, String path) {
        return new ErrorResponse(Instant.now(), status, error, code, message, path, null);
    }

    public static ErrorResponse withFieldErrors(int status, String error, String code,
                                                 String message, String path, List<FieldError> fieldErrors) {
        return new ErrorResponse(Instant.now(), status, error, code, message, path, fieldErrors);
    }

    /**
     * Field-level validation error detail.
     */
    public record FieldError(
        String field,
        String message,
        Object rejectedValue
    ) {}
}