package com.manittotie.manilib.groups.repository;

import com.manittotie.manilib.groups.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Long> {
}
