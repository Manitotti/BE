package com.manittotie.manilib.groups.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.groups.dto.*;
import com.manittotie.manilib.groups.service.GroupService;
import com.manittotie.manilib.member.dto.KickMemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GroupController {

    @Autowired
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

    @Operation(summary = "그룹 가입 신청 API", description = "특정 그룹에 가입을 신청하는 API입니다.")
    @PostMapping("/groups/{groupId}/request")
    public ResponseEntity<String> requestGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        groupService.requestGroup(groupId, email);
        return ResponseEntity.ok("그룹에 가입을 신청했습니다.");
    }


    // 내 그룹 조회
    @Operation(summary = "내가 속한 그룹 조회", description = "로그인한 사용자가 속한 그룹들을 조회합니다.")
    @GetMapping("/group")
    public ResponseEntity<?> getMyGroups(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        List<MyGroupResponse> response = groupService.getMyGroups(memberId);
        return ResponseEntity.ok(response);
    }

    // 전체 그룹 조회
    @Operation(summary = "전체 그룹 조회", description = "모든 그룹들을 조회합니다.")
    @GetMapping("/groups")
    public ResponseEntity<?> getAllGroups() {
        List<MyGroupResponse> response = groupService.getAllGroups();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특정 그룹 삭제 API", description = "관리자가 본인 그룹을 삭제하는 API입니다.")
    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<MessageResponse> deleteGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        groupService.deleteGroup(groupId, memberDetails.getMember());
        return ResponseEntity.ok(new MessageResponse("그룹이 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "멤버 추방 API", description = "관리자가 그룹 멤버를 추방하는 API입니다.")
    @DeleteMapping("/groups/{groupId}/kick")
    public ResponseEntity<?> kickMember (
            @PathVariable Long groupId,
            @RequestBody KickMemberRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        groupService.kickMember(groupId, request.getMemberId(), memberDetails.getMember());
        return ResponseEntity.ok(new MessageResponse("멤버가 성공적으로 추방되었습니다."));
    }

    @Operation(summary = "그룹 가입 신청 승인 API", description = "관리자가 그룹 멤버 가입 요청을 승인하는 API입니다.")
    @PostMapping("/{groupId}/join/requests/{requestId}/approve")
    public ResponseEntity<?> approveJoinRequest(
            @PathVariable Long groupId,
            @PathVariable Long requestId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        groupService.approveJoinRequest(groupId, requestId, memberDetails.getMember());
        return ResponseEntity.ok(new MessageResponse("가입 신청 승인"));
    }
    
    @Operation(summary = "그룹 가입 신청 거절 API", description = "관리자가 그룹 멤버 가입 요청을 거절하는 API입니다.")
    @PostMapping("/{groupId}/join/requests/{requestId}/reject")
    public ResponseEntity<?> rejectJoinRequest(
            @PathVariable Long groupId,
            @PathVariable Long requestId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        groupService.rejectJoinRequest(groupId, requestId, memberDetails.getMember());
        return ResponseEntity.ok(new MessageResponse("가입 신청 거절"));
    }
    
    @Operation(summary = "그룹 가입 신청 목록 조회 API", description = "관리자가 본인 그룹에 대한 가입 신청 목록을 볼 수 있는 API입니다.")
    @GetMapping("/groups/{groupId}/request")
    public ResponseEntity<List<JoinRequestDto>> getGroupJoinRequest (
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        List<JoinRequestDto> requests = groupService.getJoinRequests(groupId, memberDetails.getMember());
        return ResponseEntity.ok(requests);
    }

}
