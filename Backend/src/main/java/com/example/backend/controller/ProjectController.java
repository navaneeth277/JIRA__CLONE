package com.example.backend.controller;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.*;
import com.example.backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectResponse createProject(
            @Valid
            @RequestBody
            CreateProjectRequest request,
            Authentication authentication) {

        return projectService.createProject(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public PageResponse<ProjectResponse> getProjects(
            Authentication authentication,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "")
            String search) {

        return projectService.getProjects(
                authentication.getName(),
                page,
                size,
                search
        );
    }

    @PutMapping("/{projectId}")
    public ProjectResponse updateProject(
            @PathVariable Long projectId,

            @RequestBody
            UpdateProjectRequest request,

            Authentication authentication) {

        return projectService.updateProject(
                projectId,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/{projectId}")
    public void deleteProject(
            @PathVariable Long projectId,
            Authentication authentication) {

        projectService.deleteProject(
                projectId,
                authentication.getName()
        );
    }
}