package com.senai.lecture.zero.from.job.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableServiceImplTest {

    TableServiceImpl tableService = new TableServiceImpl();


    @Test
    void testSomaWhenGivenValuesReturnFour() {

        int result = tableService.soma(2, 2);

        assertEquals(4, result);
    }
}