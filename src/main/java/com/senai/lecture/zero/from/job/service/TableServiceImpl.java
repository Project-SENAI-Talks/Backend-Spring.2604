package com.senai.lecture.zero.from.job.service;

import com.senai.lecture.zero.from.job.dao.UserDAO;
import com.senai.lecture.zero.from.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl  {

    private final  UserRepository userRepository;

    public List<UserDAO> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDAO getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
