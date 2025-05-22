package com.manittotie.manilib.groups.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.groups.dto.CreateGroupRequest;
import com.manittotie.manilib.groups.dto.CreateGroupResponse;
import com.manittotie.manilib.groups.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "그룹 가입 API", description = "특정 그룹에 가입하는 API입니다.")
    @PostMapping("/groups/{groupId}/join")
    public ResponseEntity<String> joinGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        groupService.joinGroup(groupId, email);
        return ResponseEntity.ok("그룹에 가입되었습니다.");
    }
}
