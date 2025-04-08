package com.senai.lecture.zero.from.job.repository;

import com.senai.lecture.zero.from.job.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
