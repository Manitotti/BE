package com.manittotie.manilib.manitottipost.repository;

import com.manittotie.manilib.manitottipost.domain.ManitottiComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<ManitottiComment, Long> {
    List<ManitottiComment> findByPost_Id(Long postId);
    long countByPost_Id(Long postId);
}
