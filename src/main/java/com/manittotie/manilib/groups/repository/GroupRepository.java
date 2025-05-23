package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Groups, Long> {
    // 내가 속한 모든 그룹
    //테스트!!
    // 다시 테스트!
    // 테스트!
    List<Groups> findByMemberGroups_Member_Id(Long memberId);

}
