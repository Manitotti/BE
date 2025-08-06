package com.manittotie.manilib.groups.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class JoinRequestDto {
    private Long requestId;
    private String nickname;
    private String createdAt;

    public JoinRequestDto(Long requestId, String nickname, LocalDateTime createdAt){
        this.requestId = requestId;
        this.nickname = nickname;
        this.createdAt = createdAt != null ? createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : null;
    }
}
