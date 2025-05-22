package com.manittotie.manilib.groups.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateGroupResponse {
    private Long groupId;
    private String groupName;
    private String description;
    private Long adminId;
}
