package com.manittotie.manilib.auth.controller;

import com.manittotie.manilib.auth.jwt.JwtUtil;
import com.manittotie.manilib.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1")
@Tag(name = "유저 관련 API")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

//    @Operation(summary = "로그인 API", description = "로그인 시도시, 실제 DB에 있는지 확인후 반환해주는 API 입니다. ")
//    @PostMapping("/signup")
//    public ResponseEntity<?> login(
//            @RequestParam(value="email") String email,
//            @RequestParam(value = "password") String password
//    ) {
//        if(authService.login(email, password)) {
//            return ResponseEntity.ok("로그인 성공");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디나 비밀번호를 잘못 입력하셨습니다.");
//    }

}
