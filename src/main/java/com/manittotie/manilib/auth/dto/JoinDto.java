package com.manittotie.manilib.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class JoinDto {
    private String email;
    private String password;
    private String nickname;
}
