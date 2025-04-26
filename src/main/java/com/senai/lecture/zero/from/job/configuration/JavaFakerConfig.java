package com.senai.lecture.zero.from.job.configuration;

import com.github.javafaker.Faker;
import com.senai.lecture.zero.from.job.model.entity.CustomerEntity;
import com.senai.lecture.zero.from.job.model.entity.RoleEntity;
import com.senai.lecture.zero.from.job.model.entity.UserEntity;
import com.senai.lecture.zero.from.job.model.entity.enumerator.UserRole;
import com.senai.lecture.zero.from.job.repository.JackpotCustomerRepository;
import com.senai.lecture.zero.from.job.repository.JackpotUserRepository;
import com.senai.lecture.zero.from.job.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class JavaFakerConfig {

    private final PasswordEncoder encoder;

    @Bean
    public CommandLineRunner fakerCustomers(JackpotCustomerRepository jackpotCustomerRepository) {
        return args -> {
            Faker faker = new Faker();

            List<CustomerEntity> customerEntities = IntStream.range(0, 50)
                    .mapToObj(i -> {
                        return CustomerEntity.builder()
                                .name(faker.friends().character())
                                .email(faker.internet().emailAddress())
                                .age(faker.number().numberBetween(1, 110))
                                .build();
                    })
                    .toList();

            jackpotCustomerRepository.saveAll(customerEntities);
        };
    }

    @Bean
    public CommandLineRunner fakerRoles(RoleRepository roleRepository) {
        return args -> {
            RoleEntity adminRole = RoleEntity.builder()
                    .roleId(1L)
                    .name("ROLE_" + UserRole.ADMIN.getValue())
                    .build();
            RoleEntity userRole = RoleEntity.builder()
                    .roleId(2L)
                    .name("ROLE_" + UserRole.USER.getValue())
                    .build();

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        };
    }

    @Bean
    public CommandLineRunner fakerUsers(JackpotUserRepository jackpotUserRepository, RoleRepository roleRepository) {
        return args -> {
            Faker faker = new Faker();

            List<UserEntity> adminUserEntities = IntStream.range(0, 1)
                    .mapToObj(i -> {
                        return UserEntity.builder()
                                .username("admin123")
                                .password(encoder.encode("1234567890123"))
                                .build();
                    })
                    .toList();

            List<UserEntity> userEntities = IntStream.range(0, 1)
                    .mapToObj(i -> {
                        return UserEntity.builder()
                                .username("user123")
                                .password(encoder.encode("1234567890123"))
                                .build();
                    })
                    .toList();

            jackpotUserRepository.saveAll(adminUserEntities);
            jackpotUserRepository.saveAll(userEntities);
        };
    }

}
