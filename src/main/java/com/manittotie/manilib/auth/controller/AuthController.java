package com.manittotie.manilib.auth.controller;

import com.manittotie.manilib.auth.dto.JoinDto;
import com.manittotie.manilib.auth.dto.LoginRequest;
import com.manittotie.manilib.auth.jwt.JwtUtil;
import com.manittotie.manilib.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1")
@Tag(name = "유저 관련 API")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "로그인 API", description = "로그인 시도시, 실제 DB에 있는지 확인후 반환해주는 API 입니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + token)
                .body(Map.of(
                        "message", "로그인 성공",
                        "token", token
                ));
    }

    @Operation(summary = "회원가입 API", description = "이메일, 비밀번호, 닉네임 입력 후 회원가입하는 API입니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDto joinDto) {
        log.info("signup email: {}", joinDto.getEmail());
        authService.joinProcess(joinDto);
        return ResponseEntity.ok(Map.of("email", joinDto.getEmail()));

    }

}
