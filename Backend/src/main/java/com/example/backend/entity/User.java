package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * USER NAME
     */
    @Column(nullable = false)
    private String name;

    /*
     * UNIQUE EMAIL
     */
    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    /*
     * ENCRYPTED PASSWORD
     */
    @Column(nullable = false)
    private String password;

    /*
     * ACCOUNT CREATED TIME
     */
    private LocalDateTime createdAt;

    /*
     * PROJECTS OWNED BY USER
     *
     * ONE USER
     *      ↓
     * MANY PROJECTS
     */
    @OneToMany(
            mappedBy = "owner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Project> projects =
            new ArrayList<>();

    /*
     * PROJECT MEMBERSHIPS
     *
     * ONE USER
     *      ↓
     * MANY PROJECT MEMBERSHIPS
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<ProjectMember> memberships =
            new ArrayList<>();

    /*
     * AUTO CREATE TIMESTAMP
     */
    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();
    }
}