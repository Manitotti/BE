package com.manittotie.manilib.member.service;

import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(String email, String encodedPassword, String nickname) {
        Member newMember = Member.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .build();
        memberRepository.save(newMember);
    }
}
