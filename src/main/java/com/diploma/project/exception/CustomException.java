package com.diploma.project.exception;


import com.diploma.project.exception.constants.ExceptionConstants;

public class CustomException extends RuntimeException {

    private final ExceptionConstants exceptionCode;

    public CustomException(ExceptionConstants code) {
        this(code, code.getTemplateMessage());
    }

    public CustomException(ExceptionConstants code, String message) {
        super(message);
        this.exceptionCode = code;
    }

    public ExceptionConstants getExceptionCode() {
        return exceptionCode;
    }
}
