package com.senai.lecture.zero.from.job.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.lecture.zero.from.job.model.entity.CustomerEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "Customer", description = "Customer information", required = true)
public class CustomerDTO {
    @Schema(name = "name", description = "Person name", example = "John Doe")
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Schema(name = "email", description = "Person email", example = "johndoe@example.com")
    private String email;

    @Schema(name = "age", description = "Person age", example = "45", defaultValue = "17" )
    private Integer age;

    public static CustomerDTO convertUserEntityToDTO(CustomerEntity customerEntity) {
        return CustomerDTO.builder()
                .name(customerEntity.getName())
                .email(customerEntity.getEmail())
                .age(customerEntity.getAge())
                .build();
    }

}
