package com.example.backend.dto.request;

import com.example.backend.entity.MemberRole;
import lombok.Data;

@Data
public class UpdateMemberRoleRequest {

    private MemberRole role;
}