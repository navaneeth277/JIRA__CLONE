package com.example.backend.service.impl;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.ProjectMemberResponse;
import com.example.backend.entity.*;
import com.example.backend.exception.*;
import com.example.backend.repository.*;
import com.example.backend.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberServiceImpl
        implements ProjectMemberService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final ProjectMemberRepository
            projectMemberRepository;

    @Override
    public ProjectMemberResponse inviteMember(
            Long projectId,
            InviteMemberRequest request,
            String currentUserEmail) {

        Project project =
                validateProjectAdmin(
                        projectId,
                        currentUserEmail
                );

        User invitedUser =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                ));

        boolean alreadyExists =
                projectMemberRepository
                        .findByProjectAndUser(
                                project,
                                invitedUser
                        )
                        .isPresent();

        if (alreadyExists) {

            throw new BadRequestException(
                    "User already member"
            );
        }

        ProjectMember member =
                ProjectMember.builder()
                        .project(project)
                        .user(invitedUser)
                        .role(
                                MemberRole.PROJECT_MEMBER
                        )
                        .build();

        projectMemberRepository.save(member);

        return mapToResponse(member);
    }

    @Override
    public List<ProjectMemberResponse> getMembers(
            Long projectId,
            String currentUserEmail) {

        Project project =
                validateProjectAccess(
                        projectId,
                        currentUserEmail
                );

        return projectMemberRepository
                .findByProject(project)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProjectMemberResponse updateRole(
            Long memberId,
            UpdateMemberRoleRequest request,
            String currentUserEmail) {

        ProjectMember member =
                projectMemberRepository
                        .findById(memberId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"
                                ));

        validateProjectAdmin(
                member.getProject().getId(),
                currentUserEmail
        );

        member.setRole(request.getRole());

        projectMemberRepository.save(member);

        return mapToResponse(member);
    }

    @Override
    public void removeMember(
            Long memberId,
            String currentUserEmail) {

        ProjectMember member =
                projectMemberRepository
                        .findById(memberId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"
                                ));

        validateProjectAdmin(
                member.getProject().getId(),
                currentUserEmail
        );

        projectMemberRepository.delete(member);
    }

    private Project validateProjectAdmin(
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
                    "Admin access required"
            );
        }

        return project;
    }

    private Project validateProjectAccess(
            Long projectId,
            String email) {

        Project project =
                projectRepository.findById(projectId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Project not found"
                                ));

        boolean isOwner =
                project.getOwner()
                        .getEmail()
                        .equals(email);

        if (isOwner) {

            return project;
        }

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

        boolean memberExists =
                projectMemberRepository
                        .findByProjectAndUser(
                                project,
                                user
                        )
                        .isPresent();

        if (!memberExists) {

            throw new BadRequestException(
                    "Access denied"
            );
        }

        return project;
    }

    private ProjectMemberResponse mapToResponse(
            ProjectMember member) {

        return ProjectMemberResponse.builder()
                .id(member.getId())
                .name(member.getUser().getName())
                .email(member.getUser().getEmail())
                .role(member.getRole())
                .build();
    }
}