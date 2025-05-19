package com.manittotie.manilib.auth.dto;

import com.manittotie.manilib.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Member member;

    public Long getId() {
        return member.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 역할 정보 사용 안 함 → 빈 권한 컬렉션 리턴
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // username은 Spring Security 기준 식별자 (email 사용)
    }

    public String getNickname() {
        return member.getNickname();
    }

    public String getEmail() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 X
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠김 없음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 X
    }

    @Override
    public boolean isEnabled() {
        return true; // 활성 계정
    }
}
