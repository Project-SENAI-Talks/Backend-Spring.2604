package com.senai.lecture.zero.from.job.service.impl;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.CustomerDTO;
import com.senai.lecture.zero.from.job.model.entity.CustomerEntity;
import com.senai.lecture.zero.from.job.repository.JackpotCustomerRepository;
import com.senai.lecture.zero.from.job.service.JackpotCustomerService;
import com.senai.lecture.zero.from.job.utils.VerifyNullFieldsUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotCustomerServiceImpl implements JackpotCustomerService {

    private final JackpotCustomerRepository jackpotCustomerRepository;

    @Override
    public Page<CustomerDTO> getAllCustomers(Pageable users) {
        return jackpotCustomerRepository.findAll(users).map(CustomerDTO::convertUserEntityToDTO);
    }

    @Override
    public Optional<CustomerDTO> getCustomerByIdentityId(Long identityId) {
        log.info("Get user from id: {}.", identityId);

        Optional<CustomerEntity> customer = jackpotCustomerRepository.findCustomerCustomerId(identityId);
        if (customer.isPresent()) {
            return customer.map(CustomerDTO::convertUserEntityToDTO);
        }
        throw new UserNotFoundException(String.format("User with id %s not found.", identityId));
    }

    @Override
    public CustomerDTO registerCustomer(CustomerDTO customerDto) {
        CustomerEntity customerEntity = CustomerEntity.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .age(customerDto.getAge())
                .build();

        log.info("Register done successfully!");
        jackpotCustomerRepository.save(customerEntity);

        return customerDto;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDto, Long id) throws UserNotFoundException {
        log.info("Update user infos from id {}.", id);

        CustomerEntity updatedToBeCustomerEntity = jackpotCustomerRepository.findCustomerCustomerId(id).orElse(null);
        if (updatedToBeCustomerEntity == null) {
            throw new UserNotFoundException(String.format("User with email %s not found.", customerDto.getEmail()));
        }

        VerifyNullFieldsUtil.updateIfPresent(updatedToBeCustomerEntity::setName, customerDto.getName());
        VerifyNullFieldsUtil.updateIfPresent(updatedToBeCustomerEntity::setEmail, customerDto.getEmail());
        VerifyNullFieldsUtil.updateIfPresent(updatedToBeCustomerEntity::setAge, customerDto.getAge());

        log.info("Update done successfully!");
        jackpotCustomerRepository.save(Objects.requireNonNull(updatedToBeCustomerEntity));

        return customerDto;
    }

    @Override
    public void deleteCustomerByIdentityId(Long identityId) throws UserNotFoundException {
        log.info("Delete user from id: {}.", identityId);

        Optional<CustomerEntity> customerToBeDeleted = jackpotCustomerRepository.findCustomerCustomerId(identityId);

        if (customerToBeDeleted.isPresent()) {
            jackpotCustomerRepository.deleteById(customerToBeDeleted.get().getCustomerId());
        } else {
            throw new EntityNotFoundException(String.format("User with id %s not found.", identityId));
        }
        log.info("Delete done successfully!");

    }

}
