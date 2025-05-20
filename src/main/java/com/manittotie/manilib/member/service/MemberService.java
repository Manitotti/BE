package com.manittotie.manilib.member.service;

import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.dto.DuplicateEmailRequest;
import com.manittotie.manilib.member.dto.DuplicateEmailResponse;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public DuplicateEmailResponse hasEmail(DuplicateEmailRequest request) {
        Optional<Member> member = memberRepository.findByEmail(request.getEmail());
        if(member.isPresent()) {
            return DuplicateEmailResponse.createWith(true);
        } else {
            return DuplicateEmailResponse.createWith(false);
        }
    }
}
