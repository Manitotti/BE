package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGroupRepository  extends JpaRepository<MemberGroups, Long> {
    boolean existsByMemberAndGroups(Member member, Groups group);
    List<Groups> findAllByMember_Id(Long memberId);

    List<MemberGroups> findByMember_Id(Long memberId);
    List<MemberGroups> findByGroups_Id(Long groupId);

    // 그룹 가입 - 이미 가입한 회원인지 확인
    boolean existsByGroupsAndMember(Groups groups, Member member);
}
