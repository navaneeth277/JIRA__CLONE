package com.example.backend.service;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.ProjectResponse;
import com.example.backend.dto.response.PageResponse;

public interface ProjectService {

    ProjectResponse createProject(
            CreateProjectRequest request,
            String email
    );

    PageResponse<ProjectResponse> getProjects(
            String email,
            int page,
            int size,
            String search
    );

    ProjectResponse updateProject(
            Long projectId,
            UpdateProjectRequest request,
            String email
    );

    void deleteProject(
            Long projectId,
            String email
    );
}