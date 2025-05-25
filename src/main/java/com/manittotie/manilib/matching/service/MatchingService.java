package com.manittotie.manilib.matching.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.groups.repository.MemberGroupRepository;
import com.manittotie.manilib.matching.domain.MatchingResult;
import com.manittotie.manilib.matching.domain.MatchingSession;
import com.manittotie.manilib.matching.dto.MatchingResultDto;
import com.manittotie.manilib.matching.dto.MatchingStatusResponse;
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
    public MatchingStatusResponse startMatching(Long groupId, Member admin) {
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

        // 매칭 시작 전, 이전 세션 결과 전부 삭제하고 새로 저장하는 걸로 바꾸기...
        List<MatchingSession> existingSessions = matchingSessionRepository.findAllByGroupsId(groupId);
        for (MatchingSession s : existingSessions) {
            matchingResultRepository.deleteAllBySessionId(s.getId());
        }

        matchingSessionRepository.deleteAll(existingSessions);

        // 매칭을 위한 시드 생성
        long seed = System.currentTimeMillis();
        Collections.shuffle(memberList, new Random(seed));

        MatchingSession session = MatchingSession.builder()
                .groups(group)
                .seed(seed)
                .isRevealed(false)
                .build();

        matchingSessionRepository.save(session);
        matchingSessionRepository.flush();

        List<MatchingResult> results = new ArrayList<>();
        List<MatchingResultDto> resultDtos = new ArrayList<>();
        for(int i = 0; i < memberList.size(); i++) {
            Member giver = memberList.get(i);
            Member receiver = memberList.get((i + 1) % memberList.size());

            MatchingResult result = new MatchingResult();
            result.setSession(session);
            result.setGiver(giver);
            result.setReceiver(receiver);
            results.add(result);

            resultDtos.add(new MatchingResultDto(
                    giver.getId(),
                    giver.getNickname(),
                    receiver.getId(),
                    receiver.getNickname()));
        }

        matchingResultRepository.saveAll(results);

        return new MatchingStatusResponse(
                true,
                false,
                resultDtos
        );

    }

    // 마니또 매칭 후 결과 공개 서비스 (조회와 별개)
    public String revealMatching(Long groupId, Member admin) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        if(!group.getAdmin().getId().equals(admin.getId())) {
            throw new AccessDeniedException("공개 권한이 없습니다.");
        }

        MatchingSession session = matchingSessionRepository.findTopByGroups_IdOrderByCreatedAtDesc(groupId)
                .orElseThrow(()-> new IllegalArgumentException("매칭 결과가 존재하지 않습니다."));
        session.setRevealed(true);
        matchingSessionRepository.save(session);

        return "마니또 결과 공개";
    }

    // 마니또 매칭 결과 조회 서비스
    public List<MatchingResultDto> getMatchingResult(Long groupId, Member member) {
        MatchingSession session = matchingSessionRepository.findTopByGroups_IdAndIsRevealedTrueOrderByCreatedAtDesc(groupId)
                .orElseThrow(()-> new IllegalArgumentException("매칭 결과가 존재하지 않습니다."));

        boolean isAdmin = session.getGroups().getAdmin().getId().equals(member.getId());
        if(!session.isRevealed() && !isAdmin) {
            throw new AccessDeniedException("매칭 결과가 공개되지 않았습니다.");
        }

        return matchingResultRepository.findAllBySessionId(session.getId()).stream()
                .map(r -> new MatchingResultDto(
                        r.getGiver().getId(),
                        r.getGiver().getNickname(),
                        r.getReceiver().getId(),
                        r.getReceiver().getNickname()))
                .collect(Collectors.toList());
    }

}
