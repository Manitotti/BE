package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Groups, Long> {
    List<Groups> findAllByMember_Id(Long memberId);

}
