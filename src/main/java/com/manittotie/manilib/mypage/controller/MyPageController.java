package com.manittotie.manilib.mypage.controller;


import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.mypage.dto.MyPageResponse;
import com.manittotie.manilib.mypage.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    @GetMapping()
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getId();
        MyPageResponse response = myPageService.GetMyPage(memberId);

        return ResponseEntity.ok(response);
    }

}
