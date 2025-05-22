package com.manittotie.manilib.matching.repository;

import com.manittotie.manilib.matching.domain.MatchingResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingResultRepository extends JpaRepository<MatchingResult, Long> {
    List<MatchingResult> findAllBySessionId(Long sessionId);

}
