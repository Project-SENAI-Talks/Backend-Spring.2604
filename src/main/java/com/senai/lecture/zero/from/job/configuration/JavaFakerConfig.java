package com.senai.lecture.zero.from.job.configuration;

import com.github.javafaker.Faker;
import com.senai.lecture.zero.from.job.model.dto.UserDTO;
import com.senai.lecture.zero.from.job.model.entity.User;
import com.senai.lecture.zero.from.job.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class JavaFakerConfig {
    UserDTO user = new UserDTO();

    @Bean
    public CommandLineRunner faker(UserRepository userRepository) {
        return args -> {
            Faker faker = new Faker();

            List<User> users = IntStream.range(0, 10)
                    .mapToObj(i -> {
                        User user = User.builder()
                                .name(faker.friends().character())
                                .email(faker.internet().emailAddress())
                                .age(faker.number().numberBetween(1, 110))
                                .build();
                        return user;
                    })
                    .toList();

            userRepository.saveAll(users);
        };
    }
}
