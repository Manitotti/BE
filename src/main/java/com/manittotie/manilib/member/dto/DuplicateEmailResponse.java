package com.manittotie.manilib.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DuplicateEmailResponse {
    private boolean isDuplicated;

    public static DuplicateEmailResponse createWith(boolean isDuplicated) {
        return DuplicateEmailResponse.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}
