package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class ManitottiCommentResponse {
    private Long id;
    private String content;
    private String writer; // 익명 처리
    private String createdAt;
    private String updatedAt;

    public ManitottiCommentResponse(Long id, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.writer = "익명";
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
        this.updatedAt = updatedAt != null ? updatedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }
}
