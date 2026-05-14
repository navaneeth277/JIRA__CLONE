package com.example.backend.controller;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.ProjectMemberResponse;
import com.example.backend.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService
            projectMemberService;

    @PostMapping("/{projectId}/members")
    public ProjectMemberResponse inviteMember(
            @PathVariable Long projectId,

            @Valid
            @RequestBody
            InviteMemberRequest request,

            Authentication authentication) {

        return projectMemberService.inviteMember(
                projectId,
                request,
                authentication.getName()
        );
    }

    @GetMapping("/{projectId}/members")
    public List<ProjectMemberResponse> getMembers(
            @PathVariable Long projectId,
            Authentication authentication) {

        return projectMemberService.getMembers(
                projectId,
                authentication.getName()
        );
    }

    @PutMapping("/members/{memberId}/role")
    public ProjectMemberResponse updateRole(
            @PathVariable Long memberId,

            @RequestBody
            UpdateMemberRoleRequest request,

            Authentication authentication) {

        return projectMemberService.updateRole(
                memberId,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/members/{memberId}")
    public void removeMember(
            @PathVariable Long memberId,
            Authentication authentication) {

        projectMemberService.removeMember(
                memberId,
                authentication.getName()
        );
    }
}