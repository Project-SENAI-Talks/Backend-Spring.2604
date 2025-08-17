package com.senai.lecture.zero.from.job.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.lecture.zero.from.job.configuration.jwt.JwtGenerator;
import com.senai.lecture.zero.from.job.model.dto.UserDTO;
import com.senai.lecture.zero.from.job.model.dto.response.AuthResponseDTO;
import com.senai.lecture.zero.from.job.model.entity.RoleEntity;
import com.senai.lecture.zero.from.job.model.entity.UserEntity;
import com.senai.lecture.zero.from.job.repository.JackpotUserRepository;
import com.senai.lecture.zero.from.job.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.Subject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUserControllerTest {

    @Mock
    private ObjectMapper mapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JackpotUserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtGenerator tokenGenerator;

    @InjectMocks
    private AuthUserController authUserController;

    private final UserDTO userData = UserDTO.builder()
            .username("username")
            .password("password")
            .role("ROLE_ADMIN")
            .build();

    private final RoleEntity roleEntity = RoleEntity.builder()
            .name("ROLE_ADMIN")
            .build();

    @Test
    void testRegisterUserWhenPropertiesAreMatchThenReturnStatusOk() throws JsonProcessingException {
        // Given
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("User Registered Successfully.");

        // When
        when(userRepository.existByUsername(userData.getRole())).thenReturn(Optional.empty());
        when(roleRepository.findByName(userData.getRole())).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode(userData.getPassword())).thenReturn("password-encoded");
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity());

        ResponseEntity<String> response = authUserController.registerUser(userData);

        // Then
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode(), "Status should be ACCEPTED");
        assertEquals(expectedResponse.getBody(), response.getBody(), "Request should be the expected one");
    }

    @Test
    void testRegisterUserWhenUserAlreadyExistsThenReturnStatusConflict() throws JsonProcessingException {
        // Given
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body("User Already Exists.");

        // When
        when(userRepository.existByUsername(userData.getRole())).thenReturn(Optional.of(new UserEntity()));

        ResponseEntity<String> response = authUserController.registerUser(userData);

        // Then
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode(), "Status should be CONFLICT");
        assertEquals(expectedResponse.getBody(), response.getBody(), "Request should be the expected one");
    }

    @Test
    void testAuthenticateUserWhenUserShouldBeNotifiedThenReturnStatusAccepted() throws JsonProcessingException {
        // Given
        AuthResponseDTO expectedBodyResponse = new AuthResponseDTO("token-value");
        ResponseEntity<AuthResponseDTO> expectedResponse = ResponseEntity.ok(expectedBodyResponse);

        // When
        when(mapper.writeValueAsString(userData)).thenReturn("body");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(new TestAuthentication());
        when(tokenGenerator.generateToken(any(Authentication.class))).thenReturn("token-value");

        ResponseEntity<AuthResponseDTO> response = authUserController.authenticateUser(userData);

        // Then
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode(), "Status should be CONFLICT");
        assertEquals(expectedResponse.getBody(), response.getBody(), "Request should be the expected body");
    }

    static class TestAuthentication implements Authentication {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            doNothing();
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public boolean implies(Subject subject) {
            return Authentication.super.implies(subject);
        }
    }

}