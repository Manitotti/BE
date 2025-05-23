package com.manittotie.manilib.manitottipost.service;

import com.manittotie.manilib.manitottipost.domain.ManitottiComment;
import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import com.manittotie.manilib.manitottipost.dto.AddCommentRequest;
import com.manittotie.manilib.manitottipost.dto.AddCommentResponse;
import com.manittotie.manilib.manitottipost.repository.CommentRepository;
import com.manittotie.manilib.manitottipost.repository.ManipostRepository;
import com.manittotie.manilib.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ManipostRepository manipostRepository;

    public AddCommentResponse createComment(AddCommentRequest request, Member member) {
        ManitottiPost post = manipostRepository.findById(request.getManiPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        long count = commentRepository.countByPost_Id(post.getId());
        String nickname = "익명" + (count + 1);

        ManitottiComment comment = ManitottiComment.builder()
                .unknownNickname(nickname)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        comment.setPost(post);
        comment.setMember(member);

        commentRepository.save(comment);

        AddCommentResponse response = new AddCommentResponse();

        response.setManiPostId(post.getId());
        response.setCommentId(comment.getId());
        response.setNickname(comment.getUnknownNickname());
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setUpdatedAt(null);
        response.setUpdateButton(true);
        response.setDeleteButton(true);

        return response;
    }



}
