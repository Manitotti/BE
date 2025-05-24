package com.manittotie.manilib.manitottipost.repository;

import com.manittotie.manilib.manitottipost.domain.ManitottiPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManipostRepository extends JpaRepository<ManitottiPost, Long> {
    List<ManitottiPost> findAllByGroups_Id(Long groupId);
    List<ManitottiPost> findAllByGroups_IdAndMember_Id(Long groupId, Long memberId);
    List<ManitottiPost> findAllByGroupsIdOrderByCreatedAtDesc(Long groupId);
    Optional<ManitottiPost> findById(Long id);
}


