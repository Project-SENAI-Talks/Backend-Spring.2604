package com.senai.lecture.zero.from.job.web;

import com.senai.lecture.zero.from.job.dao.UserDAO;
import com.senai.lecture.zero.from.job.service.TableServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class TableController {

    private final TableServiceImpl tableService;

    @GetMapping
    public List<UserDAO> getAllUsers() {
        return tableService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDAO getUserById(@PathVariable("id") Long id) {
        return tableService.getUserById(id);
    }
}
