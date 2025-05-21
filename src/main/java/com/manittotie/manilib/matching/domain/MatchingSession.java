package com.manittotie.manilib.matching.domain;

import com.manittotie.manilib.groups.domain.Groups;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 그룹
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups groups;

    private LocalDateTime createdAt;

    private Long seed;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingResult> results;

    @Builder
    public MatchingSession(LocalDateTime createdAt, Long seed) {
        this.createdAt = createdAt;
        this.seed = seed;
    }
}
