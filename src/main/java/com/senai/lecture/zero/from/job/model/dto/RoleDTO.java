package com.senai.lecture.zero.from.job.model.dto;

import com.senai.lecture.zero.from.job.model.entity.enumerator.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements Serializable {
    private UserRole name;
}
