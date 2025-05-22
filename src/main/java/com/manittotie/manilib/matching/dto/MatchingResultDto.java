package com.manittotie.manilib.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MatchingResultDto {
    private Long giverId;
    private String giverNickname;
    private Long receiverId;
    private String receiverNickname;
}
