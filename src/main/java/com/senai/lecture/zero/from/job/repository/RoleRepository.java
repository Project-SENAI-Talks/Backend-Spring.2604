package com.senai.lecture.zero.from.job.repository;

import com.senai.lecture.zero.from.job.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("SELECT role FROM RoleEntity role WHERE role.name = :name")
    Optional<RoleEntity> findByName(@Param("name") String name);
}
