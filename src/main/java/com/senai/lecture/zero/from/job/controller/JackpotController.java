package com.senai.lecture.zero.from.job.controller;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.UserDTO;
import com.senai.lecture.zero.from.job.model.entity.User;
import com.senai.lecture.zero.from.job.model.error.Error;
import com.senai.lecture.zero.from.job.service.impl.JackpotServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Tag(name = "Jackpot", description = "Jackpot's users")
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class JackpotController {

    private final JackpotServiceImpl tableService;

    @CrossOrigin
    @Operation(summary = "Get all users from Jackpot's system.",
            tags = {"GET"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = UserDTO.class)), description = "Return all users from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public Page<User> getAllUsers(@PageableDefault(size = 10, sort = "name", direction = ASC) Pageable users) {
        log.info("Get all Jackpot's users from Jackpot's system.");
        return tableService.getAllUsers(users);
    }

    @Operation(summary = "Get user from Jackpot's system.",
            parameters = {
                    @Parameter(name = "id", description = "User's Id", required = true)
            },
            tags = {"GET"},
            description = "It will returns an user by Id from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = UserDTO.class)), description = "It will returns an user by Id from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id) throws UserNotFoundException {
        log.info("Get user from id {}.", id);
        return tableService.getUserById(id);
    }

    @Operation(summary = "Get all users from Jackpot's system.",
            tags = {"POST"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = UserDTO.class)), description = "Return all users from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    @Transactional
    public User registerUser(@RequestBody @Validated UserDTO user) {
        log.info("Register new user.");
        return tableService.registerUser(user);
    }

    @Operation(summary = "Get all users from Jackpot's system.",
            parameters = {
                    @Parameter(name = "id", description = "User's Id", required = true)
            },
            tags = {"PUT"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = UserDTO.class)), description = "Return all users from Jackpot's system."),
                    @ApiResponse(responseCode = "404", content =
                    @Content(schema= @Schema(implementation = Error.class)), description = "User not found Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PutMapping(("/update/{id}"))
    @Transactional
    public User updateUser(@RequestBody @Validated UserDTO user, @PathVariable("id") Long id) throws UserNotFoundException {
        log.info("Update user infos from id {}.", id);
        return tableService.updateUser(user, id);
    }

    @Operation(summary = "Get all users from Jackpot's system.",
            parameters = {
                    @Parameter(name = "id", description = "User's Id", required = true)
            },
            tags = {"DELETE"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = UserDTO.class)), description = "Return all users from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @DeleteMapping("/delete/{id}")
    @Transactional
    public void deleteByIdUser(@PathVariable("id") Long id) throws UserNotFoundException {
        log.info("Delete user from id {}.", id);
        tableService.deleteByIdUser(id);
    }

}
