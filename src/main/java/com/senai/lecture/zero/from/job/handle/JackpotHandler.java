package com.senai.lecture.zero.from.job.handle;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.error.Error;
import org.apache.coyote.BadRequestException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class JackpotHandler {

    @ExceptionHandler(BadRequestException.class)
    public Error handleException(Exception e) {
        return createErrorBody(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getCause().toString());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Error handleException(UserNotFoundException e) {
        return createErrorBody(HttpStatus.NOT_FOUND.value(), e.getLocalizedMessage(), e.fillInStackTrace().toString());
    }

    private Error createErrorBody(Integer status, String message, String cause) {
        return Error.builder()
                .status(status)
                .message(message)
                .cause(cause)
                .build();
    }

}
