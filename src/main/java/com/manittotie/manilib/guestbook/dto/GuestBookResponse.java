package com.manittotie.manilib.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class GuestBookResponse {
    private String content;
    private String createdAt;

    public GuestBookResponse(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }
}
