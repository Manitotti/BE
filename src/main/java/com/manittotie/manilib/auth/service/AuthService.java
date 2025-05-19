package com.manittotie.manilib.auth.service;

import com.manittotie.manilib.member.repository.MemberRepository;
import com.manittotie.manilib.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

}
