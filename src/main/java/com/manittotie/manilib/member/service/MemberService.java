package com.manittotie.manilib.member.service;

import com.manittotie.manilib.guestbook.dto.GuestBookResponse;
import com.manittotie.manilib.matching.repository.MatchingSessionRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.dto.*;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MatchingSessionRepository matchingSessionRepository;

    // 이메일 중복 확인 서비스
    public DuplicateEmailResponse hasEmail(DuplicateEmailRequest request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        if(member.isPresent()) {
            return DuplicateEmailResponse.createWith(true);
        } else {
            return DuplicateEmailResponse.createWith(false);
        }
    }

    // 그룹 내에 멤버 전체 조회 서비스
    public GroupMemberWithMatchResponse getMemberByGroup(Long groupId) {
        List<MemberDto> members = memberRepository.findAllByGroupId(groupId).stream()
                .map(m -> new MemberDto(m.getId(), m.getEmail(), m.getNickname()))
                .collect(Collectors.toList());

        boolean isMatched = matchingSessionRepository.findTopByGroups_IdOrderByCreatedAtDesc(groupId)
                .isPresent();

        return new GroupMemberWithMatchResponse(isMatched, members);
    }

    // 마이페이지 조회
    public MyPageResponse GetMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 맴버가 존재하지 않습니다"));

        MyPageResponse response = new MyPageResponse();

        response.setNickname(member.getNickname());
        response.setEmail(member.getEmail());
        response.setPassword(member.getPassword());
        response.setMyMessage(member.getMyMessage());

        return response;
    }

    // 상태메세지 작성
    public MessageResponse WriteMyMessage(MessageRequest request, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        member.setMyMessage(request.getMessage());

        MessageResponse response = new MessageResponse();
        response.setMessage(request.getMessage());

        return response;
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long profileOwnerId, String viewerEmail) {
        Member profileOwner = memberRepository.findById(profileOwnerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        Member viewer = memberRepository.findByEmail(viewerEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        boolean isMine = profileOwner.getId().equals(viewer.getId());

        List<GuestBookResponse> guestBooks = profileOwner.getGuestBooks().stream()
                .map(guestBook -> new GuestBookResponse(guestBook.getContent(), guestBook.getCreatedAt()))
                .collect(Collectors.toList());

        return ProfileResponse.builder()
                .nickname(profileOwner.getNickname())
                .myMessage(profileOwner.getMyMessage())
                .isMine(isMine)
                .guestBooks(guestBooks)
                .build();
    }





}
