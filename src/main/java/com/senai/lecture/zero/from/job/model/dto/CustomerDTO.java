package com.senai.lecture.zero.from.job.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.lecture.zero.from.job.model.entity.CustomerEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    @Schema(name = "name", description = "Person name", example = "John Doe")
    private String name;
    @Schema(name = "email", description = "Person email", example = "johndoe@example.com")
    private String email;
    @Schema(name = "age", description = "Person age", example = "45", defaultValue = "17" )
    private Integer age;
    private String username;
    private String password;
    private String role;

    public static CustomerDTO convertUserEntityToDTO(CustomerEntity customerEntity) {
        return CustomerDTO.builder()
                .name(customerEntity.getName())
                .email(customerEntity.getEmail())
                .age(customerEntity.getAge())
                .build();
    }

}
