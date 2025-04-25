package com.senai.lecture.zero.from.job.repository;

import com.senai.lecture.zero.from.job.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JackpotUserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT user FROM UserEntity user WHERE user.username = :username")
    Optional<UserEntity> findUserByUsername(@Param("username") String username);

    @Query("SELECT user FROM UserEntity user WHERE user.username = :username")
    Optional<UserEntity> existByUsername(@Param("username") String username);

    // Vulnerable SQL injection query
    // SELECT * FROM user_data WHERE username = '"' AND password = ''"
}
