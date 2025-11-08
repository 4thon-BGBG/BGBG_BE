package com.example.bgbg.controller;

import com.example.bgbg.dto.user.LoginRequestDTO;
import com.example.bgbg.dto.user.RegisterDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.repository.user.UserRepository;
import com.example.bgbg.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
        ResponseEntity<?> loginResponse = userService.login(dto);
        return userService.login(dto);
    }
}
