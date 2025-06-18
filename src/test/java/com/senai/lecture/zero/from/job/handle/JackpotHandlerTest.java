package com.senai.lecture.zero.from.job.handle;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.error.ErrorDTO;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JackpotHandlerTest {

    @InjectMocks
    private JackpotHandler jackpotHandler;

    @Test
    void testHandleBadRequestException() {
        // Given
        BadRequestException exception = new BadRequestException();

        // When
        ResponseEntity<ErrorDTO> responseEntity = jackpotHandler.handleException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testHandleUserNotFoundException() {
        // Given
        UserNotFoundException exception = new UserNotFoundException("User not found");

        // When
        ResponseEntity<ErrorDTO> responseEntity = jackpotHandler.handleException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testHandleAuthenticationCredentialsNotFoundException() {
        // Given
        AuthenticationCredentialsNotFoundException exception = new AuthenticationCredentialsNotFoundException("User not found");

        // When
        ResponseEntity<ErrorDTO> responseEntity = jackpotHandler.handleException(exception);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    @Test
    void testHandleValidationException() {
        // Given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);

        // When
        when(exception.getBindingResult()).thenReturn(mock(BindingResult.class));

        ResponseEntity<Object> responseEntity = jackpotHandler.handleValidationException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}