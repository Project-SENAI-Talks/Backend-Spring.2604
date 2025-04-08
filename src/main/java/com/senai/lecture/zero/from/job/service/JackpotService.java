package com.senai.lecture.zero.from.job.service;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.UserDTO;
import com.senai.lecture.zero.from.job.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JackpotService {

    Page<User> getAllUsers(Pageable users);

    Optional<User> getUserById(Long id) throws UserNotFoundException;

    User registerUser(UserDTO user);

    User updateUser(UserDTO user, Long id) throws UserNotFoundException;

    void deleteByIdUser(Long id) throws UserNotFoundException;
}
