package com.manittotie.manilib.guestbook.domain;

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
public class GuestBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public GuestBook(Member owner, Member writer, String content, LocalDateTime createdAt) {
        this.owner = owner;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }
}
