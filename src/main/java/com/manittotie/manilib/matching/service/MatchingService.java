package com.manittotie.manilib.matching.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.groups.repository.MemberGroupRepository;
import com.manittotie.manilib.matching.domain.MatchingResult;
import com.manittotie.manilib.matching.domain.MatchingSession;
import com.manittotie.manilib.matching.dto.MatchingResultDto;
import com.manittotie.manilib.matching.repository.MatchingResultRepository;
import com.manittotie.manilib.matching.repository.MatchingSessionRepository;
import com.manittotie.manilib.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingService {
    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final MatchingSessionRepository matchingSessionRepository;
    private final MatchingResultRepository matchingResultRepository;

    // 마니또띠 매칭 서비스
    public List<MatchingResultDto> startMatching(Long groupId, Member admin) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        if (!group.getAdmin().getId().equals(admin.getId())) {
            throw new AccessDeniedException("매칭 권한이 없습니다.");
        }

        List<MemberGroups> memberGroup = memberGroupRepository.findAllByGroupsId(groupId);

        if(memberGroup.size() < 2) {
            throw new IllegalStateException("그룹에 멤버가 2명 이상이어야 매칭 가능합니다.");
        }

        List<Member> memberList = memberGroup.stream()
                .map(MemberGroups::getMember)
                .collect(Collectors.toList());

        // 매칭을 위한 시드 생성
        long seed = System.currentTimeMillis();
        Collections.shuffle(memberList, new Random(seed));

        MatchingSession session = MatchingSession.builder()
                .groups(group)
                .seed(seed)
                .build();

        matchingSessionRepository.save(session);

        List<MatchingResult> results = new ArrayList<>();
        for(int i = 0; i < memberList.size(); i++) {
            Member giver = memberList.get(i);
            Member receiver = memberList.get((i + 1) % memberList.size());

            MatchingResult result = new MatchingResult();
            result.setSession(session);
            result.setGiver(giver);
            result.setReceiver(receiver);
            results.add(result);
        }

        matchingResultRepository.saveAll(results);
        return results.stream()
                .map(r -> new MatchingResultDto(
                        r.getGiver().getId(),
                        r.getGiver().getNickname(),
                        r.getReceiver().getId(),
                        r.getReceiver().getNickname()))
                .collect(Collectors.toList());

    }

}
