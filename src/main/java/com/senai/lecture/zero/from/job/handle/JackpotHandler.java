package com.senai.lecture.zero.from.job.handle;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.error.ErrorDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class JackpotHandler {

    @ExceptionHandler({
            BadRequestException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity<ErrorDTO> handleException(Exception e) {
        return ResponseEntity.badRequest().body(
                createErrorBody(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("status", createErrorBody(HttpStatus.BAD_REQUEST, ex.getMessage()).getDetails().getStatus().toString());
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createErrorBody(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorBody(HttpStatus.NOT_FOUND, e.getLocalizedMessage()));
    }

    private ErrorDTO createErrorBody(HttpStatus status, String message) {
        return ErrorDTO.builder()
                .result("ERROR")
                .details(ErrorDTO.ErrorProperties.builder()
                        .status(status)
                        .message(message)
                        .build())
                .build();
    }

}
