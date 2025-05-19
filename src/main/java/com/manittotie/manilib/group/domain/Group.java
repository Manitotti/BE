package com.manittotie.manilib.group.domain;

import com.manittotie.manilib.group.listener.GroupListener;
import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import com.manittotie.manilib.matching.domain.MatchingSession;
import com.manittotie.manilib.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter @Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(GroupListener.class)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //그룹 관리자 (Member와 단방향 연관관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Member admin;

    @Column(nullable = false)
    private String groupName;

    private String description;

    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;


    // MemberGroup 연결
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberGroup> memberGroups;

    // 매칭 세션
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingSession> sessions;

    // 마니또 게시글
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ManitottiPost> posts;

//    public void updateGroup(UpdateGroupData data) {
//        this.groupName = data.getGroupName;
//        this.description = data.getDescription();
//    }

    
}

