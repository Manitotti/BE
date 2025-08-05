package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.GroupJoin;
import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupJoinRepository extends JpaRepository<GroupJoin, Long> {
    Optional<Member> findByEmail(String email);
    Optional<GroupJoin> findByMemberAndGroup(Member member, Groups group);
}
