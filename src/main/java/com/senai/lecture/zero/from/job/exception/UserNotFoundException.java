package com.senai.lecture.zero.from.job.exception;

public class UserNotFoundException extends RuntimeException {

    private String status;

    public UserNotFoundException(String message, String status) {
        super(message);
        this.status = status;
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
