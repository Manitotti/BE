package com.manittotie.manilib.groups.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AllGroupResponse {
    private Long groupId;
    private String groupName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
