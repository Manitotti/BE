package com.manittotie.manilib.guestbook.service;

import com.manittotie.manilib.guestbook.domain.GuestBook;
import com.manittotie.manilib.guestbook.dto.AddGuestBookRequest;
import com.manittotie.manilib.guestbook.dto.GuestBookResponse;
import com.manittotie.manilib.guestbook.repository.GuestBookRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class GuestBookService {
    private final MemberRepository memberRepository;
    private final GuestBookRepository guestBookRepository;

    // 방명록 작성 서비스
    public GuestBookResponse writeGuestBook(AddGuestBookRequest request, String email) {
        Member guest = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Member owner = memberRepository.findById(request.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        GuestBook guestBook = GuestBook.builder()
                .member(guest)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        guestBookRepository.save(guestBook);

        return new GuestBookResponse(
                guestBook.getContent(),
                guestBook.getCreatedAt()
        );
    }
}
