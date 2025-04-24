package com.senai.lecture.zero.from.job.model.entity.enumerator;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN", "ROLE_ADMIN"),
    USER("USER", "ROLE_USER");

    private final String value;
    private final String roleName;

    UserRole(String value, String roleName) {
        this.value = value;
        this.roleName = roleName;
    }

}
