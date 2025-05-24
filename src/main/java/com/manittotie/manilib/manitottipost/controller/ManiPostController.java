package com.manittotie.manilib.manitottipost.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.manitottipost.dto.AddManiPostRequest;
import com.manittotie.manilib.manitottipost.dto.AddManiPostResponse;
import com.manittotie.manilib.manitottipost.dto.GetManiPostResponse;
import com.manittotie.manilib.manitottipost.service.ManiPostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "그룹 내의 전체 게시글 조회 API", description = "특정 그룹의 모든 게시글들을 조회할 수 있는 API 입니다.")
    @GetMapping("/{groupId}/maniposts")
    public ResponseEntity<List<GetManiPostResponse>> getAllManiPosts(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        List<GetManiPostResponse> posts = maniPostService.getPostsByGroup(groupId, email);
        return ResponseEntity.ok(posts);
    }
}
