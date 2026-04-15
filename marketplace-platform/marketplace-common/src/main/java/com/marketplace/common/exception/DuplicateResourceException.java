package com.marketplace.common.exception;

/**
 * Exception thrown when a duplicate resource creation is attempted.
 * Maps to HTTP 409 Conflict.
 */
public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String resource, String field, Object value) {
        super("DUPLICATE_RESOURCE",
              String.format("%s with %s '%s' already exists", resource, field, value));
    }

    public DuplicateResourceException(String message) {
        super("DUPLICATE_RESOURCE", message);
    }
}