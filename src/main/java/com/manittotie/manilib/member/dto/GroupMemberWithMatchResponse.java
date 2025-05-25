package com.manittotie.manilib.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GroupMemberWithMatchResponse {
    private boolean matched;
    private List<MemberDto> members;
}
