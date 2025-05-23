package com.manittotie.manilib.manitottipost.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.groups.dto.MessageResponse;
import com.manittotie.manilib.manitottipost.dto.AddCommentRequest;
import com.manittotie.manilib.manitottipost.dto.AddCommentResponse;
import com.manittotie.manilib.manitottipost.dto.UpdateCommentRequest;
import com.manittotie.manilib.manitottipost.service.CommentService;
import com.manittotie.manilib.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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


    @Operation(summary = "댓글 삭제 API", description = "댓글을 삭제하는 API입니다.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        commentService.deleteComment(commentId, memberDetails.getMember());
        return ResponseEntity.ok(new MessageResponse("댓글이 성공적으로 삭제되었습니다."));
    }

    @Operation(summary = "댓글 수정 API", description = "댓글을 수정하는 API입니다.")
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        commentService.updateComment(commentId, request.getContent(), memberDetails.getMember());
        return ResponseEntity.ok(new MessageResponse("댓글이 성공적으로 수정되었습니다."));
    }
}
