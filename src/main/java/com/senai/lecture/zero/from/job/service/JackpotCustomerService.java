package com.senai.lecture.zero.from.job.service;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JackpotCustomerService {

    Page<CustomerDTO> getAllCustomers(Pageable users);

    Optional<CustomerDTO> getCustomerByIdentityId(Long identityId);

    CustomerDTO registerCustomer(CustomerDTO user);

    CustomerDTO updateCustomer(CustomerDTO user, Long id) throws UserNotFoundException;

    void deleteCustomerByIdentityId(Long identityId) throws UserNotFoundException;
}
