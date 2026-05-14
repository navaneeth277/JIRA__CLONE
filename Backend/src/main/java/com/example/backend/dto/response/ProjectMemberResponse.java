package com.example.backend.dto.response;

import com.example.backend.entity.MemberRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectMemberResponse {

    private Long id;

    private String name;

    private String email;

    private MemberRole role;
}