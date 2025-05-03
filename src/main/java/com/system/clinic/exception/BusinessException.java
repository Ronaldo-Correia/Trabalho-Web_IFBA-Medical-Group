package com.system.clinic.exception;

public class BusinessException extends RuntimeException {  // Altere para herdar de RuntimeException
    public BusinessException(String message) {
        super(message);
    }
}