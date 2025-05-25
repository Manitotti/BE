package com.manittotie.manilib.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MatchingStatusResponse {
    private boolean matched;
    private boolean opened;
    private List<MatchingResultDto> results;
}
