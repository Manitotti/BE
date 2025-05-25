package com.manittotie.manilib.member.service;

import com.manittotie.manilib.matching.repository.MatchingSessionRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.dto.DuplicateEmailRequest;
import com.manittotie.manilib.member.dto.DuplicateEmailResponse;
import com.manittotie.manilib.member.dto.GroupMemberWithMatchResponse;
import com.manittotie.manilib.member.dto.MemberDto;
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

}
