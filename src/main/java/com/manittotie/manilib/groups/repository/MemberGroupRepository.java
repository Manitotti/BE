package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.MemberGroups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGroupRepository  extends JpaRepository<MemberGroups, Long> {
}
