package com.senai.lecture.zero.from.job.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.CustomerDTO;
import com.senai.lecture.zero.from.job.model.error.Error;
import com.senai.lecture.zero.from.job.service.JackpotCustomerService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Tag(name = "Jackpot", description = "Jackpot's users")
@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class JackpotCustomerController {

    private final ObjectMapper mapper;
    private final JackpotCustomerService tableService;

    @CrossOrigin
    @Operation(summary = "Get all users from Jackpot's system.",
            tags = {"GET"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = CustomerDTO.class)), description = "Return all users from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public Object getAllCustomers(@PageableDefault(size = 5, sort = "name", direction = ASC, page = 0) Pageable pageable,
                                  @RequestParam(name = "metadata", defaultValue = "false") boolean includeMetadata) throws JsonProcessingException {
        log.info("GET /customers incoming call with query params: {}", mapper.writeValueAsString(pageable));

        log.info("Get all Jackpot's users from Jackpot's system.");
        Page<CustomerDTO> page = tableService.getAllCustomers(pageable);
        return includeMetadata ? page : page.getContent();
    }

    @Operation(summary = "Get user from Jackpot's system.",
            parameters = {
                    @Parameter(name = "id", description = "User's Id", required = true)
            },
            tags = {"GET"},
            description = "It will returns an user by Id from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = CustomerDTO.class)), description = "It will returns an user by Id from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerDTO>> getCustomerById(@PathVariable("id") Long identityId) throws UserNotFoundException {

        Optional<CustomerDTO> customer = tableService.getCustomerByIdentityId(identityId);
        if (customer.isEmpty()) {
            throw new UserNotFoundException(String.format("User with id %s not found.", identityId));
        }
        return ResponseEntity.ok(customer);
    }

    @Operation(summary = "Get all users from Jackpot's system.",
            tags = {"POST"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = CustomerDTO.class)), description = "Return all users from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerDTO registerCustomer(@RequestBody @Validated CustomerDTO user) {
        log.info("Register new user.");
        return tableService.registerCustomer(user);
    }

    @Operation(summary = "Get all users from Jackpot's system.",
            parameters = {
                    @Parameter(name = "id", description = "User's Id", required = true)
            },
            tags = {"PUT"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = CustomerDTO.class)), description = "Return all users from Jackpot's system."),
                    @ApiResponse(responseCode = "404", content =
                    @Content(schema= @Schema(implementation = Error.class)), description = "User not found Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @PutMapping(("/update/{id}"))
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerDTO updateCustomer(@RequestBody @Validated CustomerDTO user, @PathVariable("id") Long id) throws UserNotFoundException {
        log.info("Update user infos from id {}.", id);
        return tableService.updateCustomer(user, id);
    }

    @Operation(summary = "Get all users from Jackpot's system.",
            parameters = {
                    @Parameter(name = "id", description = "User's Id", required = true)
            },
            tags = {"DELETE"},
            description = "It will returns all users from Jackpot's system.",
            responses = {
                    @ApiResponse(responseCode = "200", content =
                    @Content(schema= @Schema(implementation = CustomerDTO.class)), description = "Return all users from Jackpot's system."),
            })
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    @DeleteMapping("/delete/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCustomerById(@PathVariable("id") Long id) throws UserNotFoundException {
        log.info("Delete user from id {}.", id);
        tableService.deleteCustomerByIdentityId(id);
    }

}
