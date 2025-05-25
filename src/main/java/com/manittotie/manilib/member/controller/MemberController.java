package com.manittotie.manilib.member.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.member.dto.*;
import com.manittotie.manilib.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        MyPageResponse response = memberService.GetMyPage(memberId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "상태메세지 작성", description = "상태메세지를 작성합니다.")
    @PostMapping("/mypage/message")
    public ResponseEntity<?> WriteMyMessage(
            @RequestBody MessageRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        MessageResponse response = memberService.WriteMyMessage(request, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "닉네임 수정", description = "닉네임을 수정합니다.")
    @PutMapping("/mypage/nickname")
    public ResponseEntity<?> UpdateNickname(
            @RequestBody NickNameRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();

        NickNameResponse response = memberService.UpdateNickname(request, email);

        return ResponseEntity.ok(response);
    }


}
