package com.example.backend.service;

import com.example.backend.dto.request.*;
import com.example.backend.dto.response.ProjectMemberResponse;

import java.util.List;

public interface ProjectMemberService {

    ProjectMemberResponse inviteMember(
            Long projectId,
            InviteMemberRequest request,
            String currentUserEmail
    );

    List<ProjectMemberResponse> getMembers(
            Long projectId,
            String currentUserEmail
    );

    ProjectMemberResponse updateRole(
            Long memberId,
            UpdateMemberRoleRequest request,
            String currentUserEmail
    );

    void removeMember(
            Long memberId,
            String currentUserEmail
    );
}