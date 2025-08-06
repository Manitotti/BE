package com.manittotie.manilib.groups.service;

import com.manittotie.manilib.groups.domain.GroupJoin;
import com.manittotie.manilib.groups.domain.Groups;
import com.manittotie.manilib.groups.domain.JoinStatus;
import com.manittotie.manilib.groups.domain.MemberGroups;
import com.manittotie.manilib.groups.dto.CreateGroupRequest;
import com.manittotie.manilib.groups.dto.CreateGroupResponse;
import com.manittotie.manilib.groups.dto.MyGroupResponse;
import com.manittotie.manilib.groups.repository.GroupJoinRepository;
import com.manittotie.manilib.groups.repository.GroupRepository;
import com.manittotie.manilib.groups.repository.MemberGroupRepository;
import com.manittotie.manilib.member.domain.Member;
import com.manittotie.manilib.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final MemberRepository memberRepository;
    private final GroupJoinRepository groupJoinRepository;

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

        memberGroupRepository.save(memberGroups);

        return new CreateGroupResponse(
                savedGroup.getId(),
                savedGroup.getGroupName(),
                savedGroup.getDescription(),
                admin.getId()
        );
    }

    // 그룹 가입 서비스 --> 대기, 승인, 거절
    // 대기
    public void requestGroup(Long groupId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        boolean alreadyJoined = memberGroupRepository.existsByMemberAndGroups(member, group);
        if(alreadyJoined) {
            throw new IllegalStateException("이미 가입 요청을 보냈습니다.");
        }


        // 가입 처리 - 요청
        GroupJoin request = GroupJoin.create(member,group);
        groupJoinRepository.save(request);
    }

    // 그룹 승인 - 관리자가 승인함
    public void approveGroup(Long groupId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        GroupJoin request = groupJoinRepository.findByMemberAndGroup(member, group)
                .orElseThrow(()-> new IllegalArgumentException("가입 요청이 존재하지 않습니다."));

        if (request.getStatus() != JoinStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        request.setStatus(JoinStatus.APPROVED);
        groupJoinRepository.save(request);

        MemberGroups join = MemberGroups.create(member, group);
    }

    // 그룹 승인 거절
    public void rejectGroup(Long groupId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("해당 그룹이 존재하지 않습니다."));

        GroupJoin joinRequest = groupJoinRepository.findByMemberAndGroup(member, group)
                .orElseThrow(()->new IllegalArgumentException("가입 요청이 존재하지 않습니다."));

        if (joinRequest.getStatus() != JoinStatus.PENDING) {
            throw new IllegalStateException("이미 처리된 요청입니다.");
        }

        joinRequest.setStatus(JoinStatus.REJECTED);
        groupJoinRepository.save(joinRequest);
    }

    // 내 그룹 조회
    public List<MyGroupResponse> getMyGroups(Long memberId) {
        List<Groups> groupsList = groupRepository.findByMemberGroups_Member_Id(memberId);
        List<MyGroupResponse> responseList = new ArrayList<>();

        for (Groups group : groupsList) {
            MyGroupResponse response = new MyGroupResponse();

            response.setGroupId(group.getId());
            response.setGroupName(group.getGroupName());
            response.setDescription(group.getDescription());
            response.setCreatedAt(group.getCreatedAt());

            responseList.add(response);
        }
        return responseList;
    }

    // 전체 그룹 조회
    public List<MyGroupResponse> getAllGroups() {
        List<Groups> groupList = groupRepository.findAll();
        List<MyGroupResponse> responseList = new ArrayList<>();

        for (Groups group : groupList) {
            MyGroupResponse response = new MyGroupResponse();

            response.setGroupId(group.getId());
            response.setGroupName(group.getGroupName());
            response.setDescription(group.getDescription());
            response.setCreatedAt(group.getCreatedAt());

            responseList.add(response);
        }
        return responseList;
    }

    // 그룹 삭제 서비스
    public void deleteGroup(Long groupId, Member admin) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        if(!group.getAdmin().getId().equals(admin.getId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        groupRepository.delete(group);
    }

    // 그룹에서 멤버 추방 서비스
    public void kickMember(Long groupId, Long kickMemberId, Member admin) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 그룹입니다."));

        if(!group.getAdmin().getId().equals(admin.getId())) {
            throw new AccessDeniedException("추방 권한이 없습니다.");
        }

        MemberGroups memberGroups = memberGroupRepository
                .findByGroupsIdAndMemberId(groupId, kickMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자는 그룹에 존재하지 않습니다."));

        if(memberGroups.getMember().getId().equals(admin.getId())) {
            throw new IllegalArgumentException("관리자는 자신을 추방할 수 없습니다.");
    }

        memberGroupRepository.delete(memberGroups);
    }

}
