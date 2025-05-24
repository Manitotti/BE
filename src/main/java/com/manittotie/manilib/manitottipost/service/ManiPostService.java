package com.manittotie.manilib.manitottipost.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.groups.repository.MemberGroupRepository;
import com.manittotie.manilib.manitottipost.domain.ManitottiComment;
import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import com.manittotie.manilib.manitottipost.dto.*;
import com.manittotie.manilib.manitottipost.repository.ManipostRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import com.manittotie.manilib.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManiPostService {
    private final ManipostRepository manipostRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;

    // 마니또띠 게시글 작성
    public AddManiPostResponse createManiPost(AddManiPostRequest request, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Groups groups = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        ManitottiPost post = new ManitottiPost();
        post.setMember(member);
        post.setGroups(groups);
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        ManitottiPost saved = manipostRepository.save(post);

        return new AddManiPostResponse(
                saved.getId(),
                groups.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

    }

    // 그룹 내의 전체 게시글 조회
    public List<GetManiPostResponse> getPostsByGroup(Long groupId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        boolean isMember = memberGroupRepository.existsByMemberAndGroups(member, group);
        if(!isMember) {
            throw new AccessDeniedException("해당 그룹 게시글에 대한 조회 권한이 없습니다.");
        }

        List<ManitottiPost> posts = manipostRepository.findAllByGroupsIdOrderByCreatedAtDesc(groupId);

        return posts.stream()
                .map(p -> new GetManiPostResponse(
                        p.getId(),
                        "익명",
                        p.getTitle(),
                        p.getContent(),
                        p.getComments() != null ? p.getComments().size() : 0, // 댓글 수 계산
                        p.getCreatedAt(),
                        p.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    // 그룹 내 특정 게시글 상세 조회
    public GetManiPostDetailResponse getPostDetail(Long groupId, Long postId, String email) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        ManitottiPost post = manipostRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if(!post.getGroups().getId().equals(groupId)) {
            throw new AccessDeniedException("게시글이 해당 그룹에 존재하지 않습니다.");
        }

        boolean isMember = memberGroupRepository.existsByMemberAndGroups(member, group);
        if(!isMember) {
            throw new AccessDeniedException("해당 그룹의 게시글에 대한 권한이 존재하지 않습니다.");
        }

        List<ManitottiCommentResponse> commentResponses = post.getComments().stream()
                .sorted(Comparator.comparing(ManitottiComment::getCreatedAt)) // 작성순으로 댓글 보이기
                .map(c -> new ManitottiCommentResponse(
                        c.getId(),
                        c.getContent(),
                        c.getCreatedAt(),
                        c.getUpdatedAt()))
                .collect(Collectors.toList());

        return new GetManiPostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                commentResponses);
    }

    // 게시글 삭제 서비스 (관리자는 강제 삭제 가능)
    public void deletePost(Long groupId, Long postId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        ManitottiPost post = manipostRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if(!post.getGroups().getId().equals(groupId)) {
            throw new AccessDeniedException("해당 그룹에 존재하지 않는 게시글입니다.");
        }

        boolean isAuthor = post.getMember().getId().equals(member.getId());
        boolean isAdmin = group.getAdmin().getId().equals(member.getId());

        if(!isAdmin && !isAuthor) {
            throw new AccessDeniedException("게시글 삭제 권한이 없습니다.");
        }

        manipostRepository.delete(post);

    }
}
