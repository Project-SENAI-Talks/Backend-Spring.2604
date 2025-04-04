package com.senai.lecture.zero.from.job.repository;

import com.senai.lecture.zero.from.job.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

}
