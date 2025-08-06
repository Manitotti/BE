package com.manittotie.manilib.groups.domain;

import com.manittotie.manilib.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "group_id")
    private Groups group;

    @Enumerated(EnumType.STRING)
    private JoinStatus status;

    private LocalDateTime createdAt;

    public static GroupJoin create(Member member, Groups group) {
        GroupJoin join = new GroupJoin();
        join.setMember(member);
        join.setEmail(member.getEmail());
        join.setGroup(group);
        join.setStatus(JoinStatus.PENDING);
        return join;
    }
}
