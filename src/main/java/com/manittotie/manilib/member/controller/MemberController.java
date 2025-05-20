package com.manittotie.manilib.member.controller;

import com.manittotie.manilib.member.dto.DuplicateEmailRequest;
import com.manittotie.manilib.member.dto.DuplicateEmailResponse;
import com.manittotie.manilib.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
