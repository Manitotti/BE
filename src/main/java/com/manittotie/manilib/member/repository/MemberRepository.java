package com.manittotie.manilib.member.repository;

import com.manittotie.manilib.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    @Query("SELECT m FROM MemberGroups mg JOIN mg.member m WHERE mg.groups.id = :groupId")
    List<Member> findAllByGroupId(@Param("groupId") Long groupId);
}
