package com.senai.lecture.zero.from.job.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    private String status;

    public UserAlreadyRegisteredException(String message, String status) {
        super(message);
        this.status = status;
    }

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

}
