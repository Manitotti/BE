package com.manittotie.manilib.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddGuestBookRequest {
    private Long memberId; // 방명록 작성자 id임!!
    private String content;
}
