package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class GetManiPostDetailResponse {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String writer; // 익명 처리
    private List<ManitottiCommentResponse> comments;

    public GetManiPostDetailResponse(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, List<ManitottiCommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = "익명";
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
        this.updatedAt = updatedAt != null ? updatedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
        this.comments = comments;
    }
}
