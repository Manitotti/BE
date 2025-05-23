package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class GetManiPostResponse {
    private Long id;
    private String writer; // 익명으로 설정해둠
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;

    public GetManiPostResponse(Long id, String writer, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.writer = "익명";
        this.title = title;
        this.content = content;
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
        this.updatedAt = updatedAt != null ? updatedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }

}
