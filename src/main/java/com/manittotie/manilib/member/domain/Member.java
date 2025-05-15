package com.manittotie.manilib.member.domain;

import com.manittotie.manilib.group.domain.MemberGroup;
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

    private String nickName;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberGroup> memberGroups;

    // ▶ MemberGroup (N : M 관계 연결해주는 middle table)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberGroup> memberGroups;

    // ▶ 마니또 게시글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManitottiPost> posts;

    // ▶ 마니또 댓글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManitottiComment> comments;

    // ▶ 방명록
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GuestBook> guestBooks;

    // ▶ Matching 결과: giver 역할
    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingResult> givenMatches;

    // ▶ Matching 결과: receiver 역할
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingResult> receivedMatches;

    @Builder
    public Member(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }

    public void updateNickname(String nickName) {
        this.nickName = nickName;
    }
}
