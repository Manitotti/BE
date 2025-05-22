package com.manittotie.manilib.groups.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MyGroupResponse {
    private Long groupId;
    private String groupName;
    private String description;
    private LocalDateTime createdAt;
    // 내가 속한 그룹 조회하고싶은건데 boolean을 쓰면 그룹마다 조회하는건가..? dto 어케 짜..
}
