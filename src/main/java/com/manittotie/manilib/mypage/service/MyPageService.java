package com.manittotie.manilib.mypage.service;

import com.manittotie.manilib.guestbook.domain.GuestBook;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import com.manittotie.manilib.mypage.dto.MyPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;

    // 마이페이지 조회
    public MyPageResponse GetMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 맴버가 존재하지 않습니다"));

        MyPageResponse response = new MyPageResponse();

        response.setNickname(member.getNickname());
        response.setEmail(member.getEmail());
        response.setPassword(member.getPassword());
        response.setMyMessage(member.getMyMessage());

        return response;
    }

}
