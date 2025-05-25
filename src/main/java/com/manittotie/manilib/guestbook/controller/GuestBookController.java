package com.manittotie.manilib.guestbook.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.guestbook.dto.AddGuestBookRequest;
import com.manittotie.manilib.guestbook.dto.GuestBookResponse;
import com.manittotie.manilib.guestbook.service.GuestBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GuestBookController {
    private final GuestBookService guestBookService;

    @Operation(summary = "방명록 작성 API", description = "사용자 프로필에 있는 방명록에 글을 작성하는 API입니다.")
    @PostMapping("/mypage/guests")
    public ResponseEntity<GuestBookResponse> writeGuestBook(
            @RequestBody AddGuestBookRequest request,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        String email = memberDetails.getEmail();
        GuestBookResponse response = guestBookService.writeGuestBook(request, email);
        return ResponseEntity.ok(response);
    }


}
