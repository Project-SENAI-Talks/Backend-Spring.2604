package com.senai.lecture.zero.from.job.controller;

import com.senai.lecture.zero.from.job.service.TableServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
public class TableController {

    private final TableServiceImpl tableService;

    @GetMapping
    public String getTable() {
        return "Hello world";
    }
}
