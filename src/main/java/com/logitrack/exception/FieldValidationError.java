package com.logitrack.exception;

public record FieldValidationError(String field, String message) {
}
