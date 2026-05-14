package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * PROJECT NAME
     */
    @Column(nullable = false)
    private String name;

    /*
     * PROJECT DESCRIPTION
     */
    @Column(length = 5000)
    private String description;

    /*
     * UNIQUE PROJECT KEY
     * Example:
     * JIRA
     * TASK
     * CRM
     */
    @Column(unique = true)
    private String projectKey;

    /*
     * TIMESTAMPS
     */
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /*
     * PROJECT OWNER
     *
     * Many projects can belong to one user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    /*
     * PROJECT MEMBERS
     *
     * One project can have many members
     */
    @OneToMany(
            mappedBy = "project",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProjectMember> members =
            new ArrayList<>();

    /*
     * AUTO TIMESTAMPS
     */
    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {

        updatedAt = LocalDateTime.now();
    }
}