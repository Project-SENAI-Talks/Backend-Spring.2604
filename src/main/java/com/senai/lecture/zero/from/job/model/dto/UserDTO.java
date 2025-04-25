package com.senai.lecture.zero.from.job.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "User", description = "User information", required = true)
public class UserDTO {

    @NotEmpty(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be at least 5 characters long")
    @Schema(name = "username", example = "johndoe", minimum = "5", maximum = "20")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 12, message = "Password must be at least 12 characters long")
    @Schema(name = "password", example = "123456789123", minimum = "12")
    private String password;

    @Schema(name = "role", example = "ADMIN", defaultValue = "USER")
    private String role;
}
