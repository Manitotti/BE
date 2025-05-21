package com.manittotie.manilib.groups.domain;

import com.manittotie.manilib.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@DynamicInsert
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGroups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Groups groups;

    @Builder
    public MemberGroups(Member member, Groups groups) {
        this.member = member;
        this.groups = groups;
    }
}
