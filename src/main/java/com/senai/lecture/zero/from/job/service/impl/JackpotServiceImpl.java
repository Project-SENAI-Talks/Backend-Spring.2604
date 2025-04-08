package com.senai.lecture.zero.from.job.service.impl;

import com.senai.lecture.zero.from.job.exception.UserNotFoundException;
import com.senai.lecture.zero.from.job.model.dto.UserDTO;
import com.senai.lecture.zero.from.job.model.entity.User;
import com.senai.lecture.zero.from.job.repository.UserRepository;
import com.senai.lecture.zero.from.job.service.JackpotService;
import com.senai.lecture.zero.from.job.utils.VerifyNullFieldsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.senai.lecture.zero.from.job.constant.JackpotConstants.ERROR_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class JackpotServiceImpl implements JackpotService {

    private final  UserRepository userRepository;

    @Override
    public Page<User> getAllUsers(Pageable users) {
        return userRepository.findAll(users).map(user -> user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ERROR_MESSAGE, id), HttpStatus.NOT_FOUND.toString())));
    }

    @Override
    public User registerUser(UserDTO userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .build();
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDto, Long id) throws UserNotFoundException {
        log.info("Update user infos from id {}.", id);

        User updatedToBeUser = getUserById(id).orElse(null);
        VerifyNullFieldsUtils.updateIfPresent(updatedToBeUser::setName, userDto.getName());
        VerifyNullFieldsUtils.updateIfPresent(updatedToBeUser::setEmail, userDto.getEmail());
        VerifyNullFieldsUtils.updateIfPresent(updatedToBeUser::setAge, userDto.getAge());

        log.info("Update done successfully!");

        return userRepository.save(Objects.requireNonNull(updatedToBeUser));
    }

    @Override
    public void deleteByIdUser(Long id) throws UserNotFoundException{
        log.info("Delete user from id {}.", id);
        Optional.ofNullable(getUserById(id)).ifPresent(user -> userRepository.deleteById(user.get().getId()));
        log.info("Delete done successfully!");
    }

}
