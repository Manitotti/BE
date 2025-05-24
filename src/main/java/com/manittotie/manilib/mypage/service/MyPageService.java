package com.manittotie.manilib.mypage.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import com.manittotie.manilib.mypage.dto.MyPageGroupResponse;
import com.manittotie.manilib.mypage.dto.MyPageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    // 마이페이지 조회
    public MyPageResponse GetMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 맴버가 존재하지 않습니다"));

        //가입한 Group 리스트 찾기
        List<Groups> groupList = groupRepository.findByMemberGroups_Member_Id(memberId);

        List<MyPageGroupResponse> groupResponses = groupList.stream().map(group -> {
            MyPageGroupResponse dto = new MyPageGroupResponse();

            dto.setGroupId(group.getId());
            dto.setGroupName(group.getGroupName());
            return dto;
        }).toList();

        MyPageResponse response = new MyPageResponse();

        response.setMemberId(member.getId());
        response.setNickname(member.getNickname());
        response.setEmail(member.getEmail());
   //     response.setPassword(member.getPassword());

        response.setGroups(groupResponses);

        return response;
    }

}
