package com.example.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProjectResponse {

    private Long id;

    private String name;

    private String description;

    private String projectKey;

    private String ownerEmail;

    private LocalDateTime createdAt;
}