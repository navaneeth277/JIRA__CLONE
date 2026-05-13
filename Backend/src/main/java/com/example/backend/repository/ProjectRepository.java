package com.example.backend.repository;

import com.example.backend.entity.Project;
import com.example.backend.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository
        extends JpaRepository<Project, Long> {

    Page<Project> findByOwnerAndNameContainingIgnoreCase(
            User owner,
            String name,
            Pageable pageable
    );
}