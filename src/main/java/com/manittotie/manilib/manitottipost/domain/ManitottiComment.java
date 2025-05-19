package com.manittotie.manilib.manitottipost.domain;

import com.manittotie.manilib.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManitottiComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manipost_id", nullable = false)
    private ManitottiPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String unknownNickname;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public ManitottiComment(String unknownNickname, String content, LocalDateTime createdAt) {
        this.unknownNickname = unknownNickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
