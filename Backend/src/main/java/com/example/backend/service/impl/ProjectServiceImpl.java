package com.example.backend.service.impl;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.*;
import com.example.backend.entity.*;
import com.example.backend.exception.*;
import com.example.backend.repository.*;
import com.example.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl
        implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectResponse createProject(
            CreateProjectRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .projectKey(generateProjectKey(
                        request.getName()
                ))
                .owner(user)
                .build();

        projectRepository.save(project);

        return mapToResponse(project);
    }

    @Override
    public PageResponse<ProjectResponse> getProjects(
            String email,
            int page,
            int size,
            String search) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("createdAt").descending()
                );

        Page<Project> projects =
                projectRepository
                        .findByOwnerAndNameContainingIgnoreCase(
                                user,
                                search,
                                pageable
                        );

        return PageResponse.<ProjectResponse>builder()
                .content(
                        projects.getContent()
                                .stream()
                                .map(this::mapToResponse)
                                .toList()
                )
                .page(projects.getNumber())
                .size(projects.getSize())
                .totalElements(projects.getTotalElements())
                .totalPages(projects.getTotalPages())
                .last(projects.isLast())
                .build();
    }

    @Override
    public ProjectResponse updateProject(
            Long projectId,
            UpdateProjectRequest request,
            String email) {

        Project project =
                validateProjectOwnership(
                        projectId,
                        email
                );

        if (request.getName() != null) {

            project.setName(request.getName());
        }

        if (request.getDescription() != null) {

            project.setDescription(
                    request.getDescription()
            );
        }

        projectRepository.save(project);

        return mapToResponse(project);
    }

    @Override
    public void deleteProject(
            Long projectId,
            String email) {

        Project project =
                validateProjectOwnership(
                        projectId,
                        email
                );

        projectRepository.delete(project);
    }

    private Project validateProjectOwnership(
            Long projectId,
            String email) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found"
                                ));

        if (!project.getOwner()
                .getEmail()
                .equals(email)) {

            throw new BadRequestException(
                    "Access denied"
            );
        }

        return project;
    }

    private ProjectResponse mapToResponse(
            Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .projectKey(project.getProjectKey())
                .ownerEmail(
                        project.getOwner().getEmail()
                )
                .createdAt(project.getCreatedAt())
                .build();
    }

    private String generateProjectKey(
            String name) {

        return name.substring(0, 3)
                .toUpperCase()
                + System.currentTimeMillis()
                % 1000;
    }
}