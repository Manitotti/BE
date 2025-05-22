package com.manittotie.manilib.manitottipost.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AddManiPostResponse {
    private Long maniPostId;
    private Long groupId;
    private Long memberId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;

    public AddManiPostResponse(Long maniPostId, Long groupId, Long memberId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.maniPostId = maniPostId;
        this.groupId = groupId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
        this.updatedAt = updatedAt != null ? updatedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }
}
