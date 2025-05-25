package com.manittotie.manilib.member.controller;

import com.manittotie.manilib.member.dto.DuplicateEmailRequest;
import com.manittotie.manilib.member.dto.DuplicateEmailResponse;
import com.manittotie.manilib.member.dto.GroupMemberWithMatchResponse;
import com.manittotie.manilib.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "이메일 중복 확인 API", description = "이메일 중복을 확인하는 API입니다.")
    @PostMapping("/duplicate/email")
    public ResponseEntity<?> duplicateEmail(@RequestBody DuplicateEmailRequest request) {
        log.info("Request to CHECK duplicate email");
        DuplicateEmailResponse response = memberService.hasEmail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "그룹 내 전체 멤버 조회 API", description = "본인이 가입한 그룹에 한해 멤버를 전체 조회할 수 있는 API입니다.")
    @GetMapping("/{groupId}/members")
    public ResponseEntity<GroupMemberWithMatchResponse> getGroupMembers(
            @PathVariable Long groupId) {
        GroupMemberWithMatchResponse response = memberService.getMemberByGroup(groupId);
        return ResponseEntity.ok(response);
    }
}
