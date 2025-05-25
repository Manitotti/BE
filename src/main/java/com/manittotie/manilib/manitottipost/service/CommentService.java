package com.manittotie.manilib.manitottipost.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.manitottipost.domain.ManitottiComment;
import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import com.manittotie.manilib.manitottipost.dto.AddCommentRequest;
import com.manittotie.manilib.manitottipost.dto.AddCommentResponse;
import com.manittotie.manilib.manitottipost.repository.CommentRepository;
import com.manittotie.manilib.manitottipost.repository.ManipostRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ManipostRepository manipostRepository;
    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    // 댓글 생성
    public AddCommentResponse createComment(AddCommentRequest request, Member member) {
        ManitottiPost post = manipostRepository.findById(request.getManiPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        long count = commentRepository.countByPost_Id(post.getId());
        String nickname = "익명";

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

        return response;
    }

    // 댓글 삭제 - 관리자는 강제 삭제 가능,
    public void deleteComment(Long commentId, Long groupId, String email) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        ManitottiComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));

        // 아이디가 멤버 아이디면 삭제하고 아니면 예외처리 멤버는 email로 받아오기
//        if(!comment.getMember().getId().equals(member.getId())) {
//            throw new AccessDeniedException("삭제 권한이 없습니다.");
//        }

        // 아이디가 관리자 아이디면 삭제하고 아니면 예외처리
        // 1. 그룹 받아오기
        // 2. 게시글

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        boolean isAdmin = group.getAdmin().getId().equals(member.getId());
        boolean isAuthor = comment.getMember().getId().equals(member.getId());

        if(!isAdmin && !isAuthor) {
            throw new AccessDeniedException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
    
    
    
    // 댓글 수정
    public void updateComment(Long commentId, String newContent, Member member) {
        ManitottiComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));

        if (!comment.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException("수정 권한 없음");
        }

        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());
    }



}
