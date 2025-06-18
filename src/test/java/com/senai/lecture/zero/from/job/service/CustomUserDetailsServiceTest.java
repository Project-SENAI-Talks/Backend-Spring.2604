package com.senai.lecture.zero.from.job.service;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.entity.RoleEntity;
import com.senai.lecture.zero.from.job.model.entity.UserEntity;
import com.senai.lecture.zero.from.job.repository.JackpotUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private JackpotUserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private final String username = "test-username";
    private final String password = "test-password";
    private final String role = "test-role";

    @Test
    void testLoadUserByUsernameWhenUserExistsThenReturnsUserDetails() {
        // Given
        final UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .roles(List.of(RoleEntity.builder().name(role).build()))
                .build();

        // When
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetails response = customUserDetailsService.loadUserByUsername(username);

        // Then
        assertEquals(username, response.getUsername(), "Username should be the expected one");
        assertEquals(password, response.getPassword(), "Password should be the expected one");

        Collection<? extends GrantedAuthority> authorities = response.getAuthorities();
        assertEquals(1, authorities.size(), "Should have exactly one authority");
        assertTrue(authorities.stream()
                        .anyMatch(auth -> auth.getAuthority().equals(role)),
                "Should contain the expected role");
    }

   @Test
    void testLoadUserByUsernameWhenUserDoesNotExistThenThrowsUsernameNotFoundException() {
        // Given
       final String expectedErrorMessage = "User with username 'test-username' not found.";

        // When && Then
       when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());
       UserNotFoundException exception = assertThrows(UserNotFoundException.class,
               () -> customUserDetailsService.loadUserByUsername(username));

       assertEquals(expectedErrorMessage, exception.getMessage(), "Error message should be the expected one");

   }
}