package com.manittotie.manilib.manitottipost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateManiPostResponse {
    private Long id;
    private Long groupId;
    private String title;
    private String content;
    private String updatedAt;

    public UpdateManiPostResponse(Long id, Long groupId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.groupId = groupId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt != null ? updatedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }
}