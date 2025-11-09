package com.example.bgbg.controller;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.response.ErrorResponseDTO;
import com.example.bgbg.dto.user.LoginRequestDTO;
import com.example.bgbg.dto.user.RegisterDTO;
import com.example.bgbg.dto.user.UserUpdateDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.repository.user.UserRepository;
import com.example.bgbg.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal(); // JWTFilter에서 User를 넣어둠
    }

    @Operation(
            summary = "회원가입",
            description = "회원가입"
    )

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        return userService.register(dto);
    }

    @Operation(
            summary = "로그인",
            description = "로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        return userService.login(dto);
    }

    @Operation(
            summary = "마이페이지",
            description = "내 정보, 장보기 내역, 나의 장보기 분석 리포트를 볼 수 있습니다."
    )
    @GetMapping("/mypage")
    public ResponseEntity<?> mypage(@AuthenticationPrincipal User loginUser) {
        if (loginUser == null) {
            return ResponseEntity.status(ErrorCode.UNAUTHORIZED_UESR.getStatus().value())
                    .body(new ErrorResponseDTO(ErrorCode.UNAUTHORIZED_UESR, null));
        }
        return userService.mypage(loginUser);
    }

    @Operation(
            summary = "유저 정보 수정",
            description = "유저 닉네임 수정"
    )
    @PatchMapping("/mypage")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateDTO dto,
                                        @AuthenticationPrincipal User loginUser) {
        return userService.updateUser(loginUser.getId(), dto);
    }
}
