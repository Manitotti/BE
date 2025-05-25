package com.manittotie.manilib.member.dto;

import com.manittotie.manilib.guestbook.dto.GuestBookResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String nickname;
    private String myMessage;
    private boolean isMine;
    private List<GuestBookResponse> guestBooks;
}
