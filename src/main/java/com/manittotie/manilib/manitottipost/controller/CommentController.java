package com.manittotie.manilib.manitottipost.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.manitottipost.dto.AddCommentRequest;
import com.manittotie.manilib.manitottipost.dto.AddCommentResponse;
import com.manittotie.manilib.manitottipost.service.CommentService;
import com.manittotie.manilib.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 생성 API", description = "댓글을 생성해주는 API입니다.")
    public ResponseEntity<?> createComment(
            @RequestBody AddCommentRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        Member member = userDetails.getMember();
        AddCommentResponse response = commentService.createComment(request, member);

        return ResponseEntity.ok(response);
    }
}
