package com.system.clinic.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationErrorDTO1 {

    private Map<String, List<ValidationFieldError>> errors = new HashMap<>();

    public ValidationErrorDTO1() {
    }

    // ✅ Construtor que recebe BindingResult
    public ValidationErrorDTO1(BindingResult bindingResult) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            this.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    public void addError(String field, String message) {
        errors.computeIfAbsent(field, k -> new ArrayList<>())
                .add(new ValidationFieldError(message));
    }

    public List<ValidationFieldError> getErrorsByField(String field) {
        return errors.getOrDefault(field, new ArrayList<>());
    }

    public Map<String, List<ValidationFieldError>> getErrors() {
        return errors;
    }

    public record ValidationFieldError(String message) {
    }
}
