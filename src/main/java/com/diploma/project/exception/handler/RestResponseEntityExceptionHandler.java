package com.diploma.project.exception.handler;

import com.diploma.project.exception.CustomException;
import com.diploma.project.exception.constants.ExceptionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<Object> handleConflict(CustomException ex, WebRequest request, Authentication authentication) {

        final ExceptionConstants constants = ex.getExceptionCode();

        log.error("\n Exception code: [{}]\n Http status: [{}]\n User  --->>> [{}]",
                constants.name(),
                constants.getHttpStatus(),
                authentication != null ? authentication.getName() : "anonymous");

        return handleExceptionInternal(ex, getBody(ex, ((ServletWebRequest) request).getRequest().getRequestURI()), new HttpHeaders(), constants.getHttpStatus(), request);
    }

    private ExceptionBody getBody(CustomException exception, String path) {

        final ExceptionConstants constants = exception.getExceptionCode();
        return ExceptionBody
                .builder()
                .path(path)
                .type(constants.getType().name())
                .code(constants.name())
                .message(exception.getMessage())
                .build();
    }
}