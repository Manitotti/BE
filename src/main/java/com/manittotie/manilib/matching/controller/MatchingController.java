package com.manittotie.manilib.matching.controller;

import com.manittotie.manilib.auth.dto.CustomUserDetails;
import com.manittotie.manilib.matching.dto.MatchingResultDto;
import com.manittotie.manilib.matching.service.MatchingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MatchingController {
    private final MatchingService matchingService;

    @Operation(summary = "마니또 매칭 API", description = "마니또, 마니띠가 1대1 관계로 매칭되는 API입니다.")
    @PostMapping("/{groupId}/matching/start")
    public List<MatchingResultDto> startMatching(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
       return matchingService.startMatching(groupId, memberDetails.getMember());
    }

    @Operation(summary = "마니또 결과 공개 API", description = "관리자가 마니또 결과를 공개하는 API입니다.")
    @PostMapping("/{groupId}/matching/reveal")
    public ResponseEntity<String> revealMatching(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        return ResponseEntity.ok(matchingService.revealMatching(groupId, memberDetails.getMember()));
    }

    @Operation(summary = "마니또 결과 조회 API", description = "마니또 결과공개 후 전체 멤버가 조회할 수 있는 API입니다.")
    @GetMapping("{groupId}/matching/results")
    public ResponseEntity<List<MatchingResultDto>> getResults(
            @PathVariable Long groupId,
            @AuthenticationPrincipal CustomUserDetails memberDetails) {
        return ResponseEntity.ok(matchingService.getMatchingResult(groupId, memberDetails.getMember()));
    }
}
