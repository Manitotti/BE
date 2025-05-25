package com.manittotie.manilib.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageResponse {

    private String nickname;
    private String email;
    private String password;

    private String myMessage;
}
