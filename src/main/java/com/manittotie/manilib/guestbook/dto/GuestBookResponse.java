package com.manittotie.manilib.guestbook.dto;

import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class GuestBookResponse {
    @OrderBy("createdAt ASC")
    private String content;

    private String createdAt;

    public GuestBookResponse(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }
}
