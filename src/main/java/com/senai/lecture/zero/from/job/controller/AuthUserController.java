package com.senai.lecture.zero.from.job.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.lecture.zero.from.job.configuration.jwt.JwtGenerator;
import com.senai.lecture.zero.from.job.model.dto.UserDTO;
import com.senai.lecture.zero.from.job.model.entity.RoleEntity;
import com.senai.lecture.zero.from.job.model.entity.UserEntity;
import com.senai.lecture.zero.from.job.model.entity.enumerator.UserRole;
import com.senai.lecture.zero.from.job.model.response.AuthResponse;
import com.senai.lecture.zero.from.job.repository.JackpotUserRepository;
import com.senai.lecture.zero.from.job.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/v1/user/oauth")
@RequiredArgsConstructor
public class AuthUserController {

    private final ObjectMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JackpotUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator tokenGenerator;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userData) throws JsonProcessingException {
        log.info("POST /register incoming call with body: {}", mapper.writeValueAsString(userData));

        if (userRepository.existByUsername(userData.getRole()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        RoleEntity role = roleRepository.findByName(userData.getRole())
                .orElse(RoleEntity.builder().name(UserRole.USER.getRoleName()).build());

        UserEntity user = UserEntity.builder()
                .username(userData.getUsername())
                .password(passwordEncoder.encode(userData.getPassword()))
                .roles(Collections.singletonList(role))
                .build();

        this.userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody UserDTO userData) throws JsonProcessingException {
        log.info("POST /login incoming call with body: {}", mapper.writeValueAsString(userData));

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userData.getUsername(),
                userData.getPassword())
        );

        System.out.println(authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
