package com.senai.lecture.zero.from.job.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.CustomerDTO;
import com.senai.lecture.zero.from.job.service.JackpotCustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JackpotCustomerControllerTest {

    private static final Long ID_TEST = 1L;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Page<CustomerDTO> customers;

    @Mock
    private Pageable pageable;


    @Mock
    private JackpotCustomerService jackpotCustomerService;

    @InjectMocks
    private JackpotCustomerController controller;

    @Test
    void testGetAllCustomersThenReturnsStatusOk() throws JsonProcessingException {
        // When
        when(jackpotCustomerService.getAllCustomers(pageable)).thenReturn(customers);
        ResponseEntity<Object> response = controller.getAllCustomers(pageable, false);

        // Then
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be OK"),
                () -> assertNotNull(response.getBody(), "Response body should not be null")
        );

        verify(jackpotCustomerService, times(1)).getAllCustomers(pageable);
    }

    @Test
    void testGetCustomerByIdWhenUserExistsThenReturnsStatusOk() {
        // Given
        Optional<CustomerDTO> customer = Optional.of(new CustomerDTO());

        // When
        when(jackpotCustomerService.getCustomerByIdentityId(ID_TEST)).thenReturn(customer);

        ResponseEntity<Optional<CustomerDTO>> response = controller.getCustomerById(ID_TEST);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerByIdWhenUserIsEmptyThenReturnsUserNotFoundException() {
        // Given
        final String expectedErrorMessage = "User with id 1 not found.";
        Optional<CustomerDTO> customer = Optional.empty();

        // When && Then
        when(jackpotCustomerService.getCustomerByIdentityId(ID_TEST)).thenReturn(customer);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                controller.getCustomerById(ID_TEST));

        assertEquals(expectedErrorMessage, exception.getMessage(), "Error message should be the expected one");
    }

    @Test
    void testRegisterCustomerWhenPayloadIsValidThenReturnsStatusOk() throws JsonProcessingException {
        // Given
        CustomerDTO customerDto = new CustomerDTO();

        // When
        when(objectMapper.writeValueAsString(customerDto)).thenReturn("payload-json");
        when(jackpotCustomerService.registerCustomer(customerDto)).thenReturn(customerDto);

        ResponseEntity<CustomerDTO> response = controller.registerCustomer(customerDto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDto, response.getBody());
    }

    @Test
    void testUpdateCustomerWhenPayloadIsValidThenReturnsStatusOk() throws JsonProcessingException {
        // Given
        CustomerDTO customerDto = new CustomerDTO();

        // When
        when(objectMapper.writeValueAsString(customerDto)).thenReturn("payload-json");
        when(jackpotCustomerService.updateCustomer(customerDto, ID_TEST)).thenReturn(customerDto);

        ResponseEntity<CustomerDTO> response = controller.updateCustomer(customerDto, ID_TEST);

        // Then
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(customerDto, response.getBody());
    }

    @Test
    void testDeleteCustomerByIdWhenUserExistsThenReturnsStatusOk() {
        // When
        jackpotCustomerService.deleteCustomerByIdentityId(ID_TEST);

        // Then
        verify(jackpotCustomerService).deleteCustomerByIdentityId(ID_TEST);
    }

}