package com.manittotie.manilib.manitottipost.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import com.manittotie.manilib.manitottipost.dto.AddManiPostRequest;
import com.manittotie.manilib.manitottipost.dto.AddManiPostResponse;
import com.manittotie.manilib.manitottipost.repository.ManipostRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import com.manittotie.manilib.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManiPostService {
    private final ManipostRepository manipostRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

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
                member.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );


    }
}
