package com.manittotie.manilib.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NickNameResponse {
    private Long memberId;
    private String nickname;
}
