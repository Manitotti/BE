package com.manittotie.manilib.guestbook.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.guestbook.dto.AddGuestBookRequest;
import com.manittotie.manilib.guestbook.dto.GuestBookResponse;
import com.manittotie.manilib.guestbook.service.GuestBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class GuestBookController {
    private final GuestBookService guestBookService;

    @PostMapping
    @Operation(summary = "방명록 작성 API", description = "방명록을 작성하는 API입니다.")
    public ResponseEntity<GuestBookResponse> writeGuestBook(
            @RequestBody AddGuestBookRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        String email = userDetails.getUsername(); // email
        GuestBookResponse response = guestBookService.writeGuestBook(request, email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}/guests")
    @Operation(summary = "방명록 조회 API", description = "특정 사용자의 방명록 목록을 조회하는 API입니다.")
    public ResponseEntity<List<GuestBookResponse>> getGuestBooks(
            @PathVariable Long memberId) {
        List<GuestBookResponse> guestBooks = guestBookService.getGuestBooks(memberId);

        return ResponseEntity.ok(guestBooks);
    }
}