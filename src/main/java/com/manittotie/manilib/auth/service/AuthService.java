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

    public String login(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!bCryptPasswordEncoder.matches(rawPassword, member.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return jwtUtil.createJwt(email); // 이메일 기반 JWT 생성
    }

    public void joinProcess(JoinDto joinDto) {

        if(memberRepository.existsByEmail(joinDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encoded = bCryptPasswordEncoder.encode(joinDto.getPassword());
        Member member = Member.builder()
                .email(joinDto.getEmail())
                .password(encoded)
                .nickname(joinDto.getNickname())
                .build();
        memberRepository.save(member);
        log.info("회원가입 완료: {}", member.getEmail());
    }


}
