package com.manittotie.manilib.mypage.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class MyPageResponse {

    private String nickname;
    private String email;
    private String password;

    private String myMessage;
}
