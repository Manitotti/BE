package com.manittotie.manilib.manitottipost.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.manitottipost.dto.AddManiPostRequest;
import com.manittotie.manilib.manitottipost.dto.AddManiPostResponse;
import com.manittotie.manilib.manitottipost.dto.GetManiPostDetailResponse;
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

    @Operation(summary = "그룹 내 특정 게시글 상세 조회", description = "그룹 안에 있는 하나의 게시글을 댓글 포함하여 상세하게 볼 수 있는 API 입니다.")
    @GetMapping("/{groupId}/maniposts/{postId}")
    public ResponseEntity<GetManiPostDetailResponse> getPostDetail(
            @PathVariable Long groupId,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        GetManiPostDetailResponse response = maniPostService.getPostDetail(groupId, postId, email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "특정 게시글 삭제", description = "오직 관리자나 작성자만이 해당 게시글을 삭제할 수 있는 API입니다.")
    @DeleteMapping("/{groupId}/maniposts/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long groupId,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        maniPostService.deletePost(groupId, postId, email);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }
}
