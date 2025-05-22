package com.manittotie.manilib.manitottipost.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.manitottipost.dto.AddManiPostRequest;
import com.manittotie.manilib.manitottipost.dto.AddManiPostResponse;
import com.manittotie.manilib.manitottipost.service.ManiPostService;
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
public class ManiPostController {
    private final ManiPostService maniPostService;

    @Operation(summary = "게시글 작성 API", description = "그룹 내에서 유저가 익명으로 글을 작성하는 API입니다.")
    @PostMapping("/maniposts")
    public ResponseEntity<AddManiPostResponse> createManiPost(
            @RequestBody AddManiPostRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails){
        String email = memberDetails.getEmail();
        AddManiPostResponse response = maniPostService.createManiPost(request, email);
        return ResponseEntity.ok(response);
    }
}
