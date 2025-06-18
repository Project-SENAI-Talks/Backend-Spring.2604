package com.senai.lecture.zero.from.job.repository;

import com.senai.lecture.zero.from.job.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JackpotCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT customer FROM CustomerEntity customer WHERE customer.customerId = :customerId")
    Optional<CustomerEntity> findCustomerCustomerId(@Param(value = "customerId") Long customerId);

    @Query("SELECT customer FROM CustomerEntity customer WHERE customer.email = :email")
    Optional<CustomerEntity> findCustomerByEmail(@Param(value = "email") String email);

    // Vulnerable SQL injection query
    // SELECT * FROM user_data WHERE username = '"' AND password = ''"
}
