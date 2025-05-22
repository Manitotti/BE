package com.manittotie.manilib.matching.repository;

import com.manittotie.manilib.matching.domain.MatchingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingSessionRepository extends JpaRepository<MatchingSession, Long> {
    List<MatchingSession> findAllByGroupsId(Long groupId);
}
