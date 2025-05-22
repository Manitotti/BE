package com.manittotie.manilib.matching.listener;

import com.manittotie.manilib.matching.domain.MatchingSession;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class MatchingSessionListener {

    @PrePersist
    public void setCreatedAt(MatchingSession session) {
        session.setCreatedAt(LocalDateTime.now());
    }
}
