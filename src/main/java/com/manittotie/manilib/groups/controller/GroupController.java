package com.manittotie.manilib.groups.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.groups.dto.CreateGroupRequest;
import com.manittotie.manilib.groups.dto.CreateGroupResponse;
import com.manittotie.manilib.groups.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GroupController {
    private final GroupService groupService;

    @Operation(summary = "그룹 생성 API", description = "그룹 이름, 설명을 입력하면 생성해주는 API입니다.")
    @PostMapping("/groups")
    public ResponseEntity<CreateGroupResponse> createGroup(
            @RequestBody CreateGroupRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        CreateGroupResponse response = groupService.createGroup(request, email);
        return ResponseEntity.ok(response);
    }
}
