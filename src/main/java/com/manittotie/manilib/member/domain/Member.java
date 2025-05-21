package com.manittotie.manilib.member.domain;

import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.guestbook.domain.GusetBook;
import com.manittotie.manilib.manitottipost.domain.ManitottiComment;
import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import com.manittotie.manilib.matching.domain.MatchingResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Set;

@Entity
@Getter
@Setter
@DynamicInsert
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String nickname;

    @Column(nullable = false)
    private String password;

    // ▶ MemberGroup (N : M 관계 연결해주는 middle table)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberGroups> memberGroups;

    // ▶ 마니또 게시글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManitottiPost> posts;

    // ▶ 마니또 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManitottiComment> comments;

    // ▶ 방명록
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GusetBook> guestBooks;

    // ▶ Matching 결과: giver 역할
    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingResult> givenMatches;

    // ▶ Matching 결과: receiver 역할
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingResult> receivedMatches;

    @Builder
    public Member(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
