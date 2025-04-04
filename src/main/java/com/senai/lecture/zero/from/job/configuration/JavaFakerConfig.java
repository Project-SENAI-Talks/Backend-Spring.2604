package com.senai.lecture.zero.from.job.configuration;

import com.github.javafaker.Faker;
import com.senai.lecture.zero.from.job.dao.UserDAO;
import com.senai.lecture.zero.from.job.model.User;
import com.senai.lecture.zero.from.job.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.IntStream;

@Configuration
public class JavaFakerConfig {
    User user = new User();

    @Bean
    public CommandLineRunner faker(UserRepository userRepository) {
        return args -> {
            Faker faker = new Faker();

            List<UserDAO> users = IntStream.range(0, 200)
                    .mapToObj(i -> {
                        UserDAO user = UserDAO.builder()
                                .name(faker.artist().name())
                                .email(faker.internet().emailAddress())
                                .age(faker.number().numberBetween(1, 10000))
                                .build();
                        return user;
                    })
                    .toList();

            userRepository.saveAll(users);
        };
    }
}
