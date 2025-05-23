package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberGroupRepository  extends JpaRepository<MemberGroups, Long> {
    boolean existsByMemberAndGroups(Member member, Groups group);
    List<Groups> findAllByMember_Id(Long memberId);

    List<MemberGroups> findByMember_Id(Long memberId);
    List<MemberGroups> findAllByGroupsId(Long groupId);

    Optional<MemberGroups> findByGroupsIdAndMemberId(Long groupId, Long memberId);
}
