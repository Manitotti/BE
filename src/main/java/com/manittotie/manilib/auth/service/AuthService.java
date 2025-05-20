package com.manittotie.manilib.auth.service;

import com.manittotie.manilib.auth.dto.JoinDto;
import com.manittotie.manilib.auth.jwt.JwtUtil;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import com.manittotie.manilib.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto) {
        String email = joinDto.getEmail();
        String password = joinDto.getPassword();
        String nickname = joinDto.getNickname();

        registerOrLoginMember(email, password, nickname);
    }

    private void registerOrLoginMember(String email, String password, String nickname) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Member existingMember = memberRepository.findByEmail(email).orElse(null);

        if (existingMember == null) {
            memberService.createMember(email, encodedPassword, nickname);
            log.info("새로운 사용자 회원가입: {}", email);
        } else {
            log.info("기존 사용자 로그인: {}", email);
        }
    }

    public String login(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!bCryptPasswordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return jwtUtil.createJwt(email); // ✅ 이메일 기반 JWT 생성
    }

}
