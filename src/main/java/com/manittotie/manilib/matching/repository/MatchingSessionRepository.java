package com.manittotie.manilib.matching.repository;

import com.manittotie.manilib.matching.domain.MatchingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchingSessionRepository extends JpaRepository<MatchingSession, Long> {
    List<MatchingSession> findAllByGroupsId(Long groupId);

    Optional<MatchingSession> findTopByGroups_IdAndIsRevealedTrueOrderByCreatedAtDesc(Long groupId);

    Optional<MatchingSession> findTopByGroups_IdOrderByCreatedAtDesc(Long groupId);
}
