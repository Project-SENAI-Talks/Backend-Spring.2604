package com.senai.lecture.zero.from.job.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "id", description = "Person id", example = "1")
    private Long id;
    @Schema(name = "name", description = "Person name", example = "John Doe")
    @Column(name = "name")
    private String name;
    @Schema(name = "email", description = "Person email", example = "johndoe@example.com")
    @Column(name = "email")
    private String email;
    @Schema(name = "age", description = "Person age", example = "45", defaultValue = "17" )
    @Column(name = "age")
    private Integer age;
}
