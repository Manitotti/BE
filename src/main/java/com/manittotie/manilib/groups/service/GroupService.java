package com.manittotie.manilib.groups.service;

import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.groups.dto.CreateGroupRequest;
import com.manittotie.manilib.groups.dto.CreateGroupResponse;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.groups.repository.MemberGroupRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;

    // 그룹 생성 서비스
    public CreateGroupResponse createGroup(CreateGroupRequest request, String email) {
        Member admin = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Groups group = Groups.builder()
                .groupName(request.getGroupName())
                .description(request.getDescription())
                .admin(admin)
                .build();

        Groups savedGroup = groupRepository.save(group);

        MemberGroups memberGroups = MemberGroups.builder()
                .member(admin)
                .groups(savedGroup)
                .build();
        return new CreateGroupResponse(
                savedGroup.getId(),
                savedGroup.getGroupName(),
                savedGroup.getDescription(),
                admin.getId()
        );
    }
}
