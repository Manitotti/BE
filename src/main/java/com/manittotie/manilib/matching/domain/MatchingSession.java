package com.manittotie.manilib.matching.domain;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.matching.listener.MatchingSessionListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(MatchingSessionListener.class)
public class MatchingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups groups;

    @Column
    private LocalDateTime createdAt;

    private Long seed;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingResult> results = new HashSet<>();

    @Column(nullable = false)
    private boolean isRevealed = false; // 결과 비공개 설정

    @Builder
    public MatchingSession(Groups groups, Long seed, boolean isRevealed) {
        this.groups = groups;
        this.seed = seed;
        this.isRevealed = isRevealed;
    }
}
