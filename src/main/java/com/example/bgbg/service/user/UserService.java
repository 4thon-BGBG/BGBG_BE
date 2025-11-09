package com.example.bgbg.service.user;

import com.example.bgbg.dto.user.LoginRequestDTO;
import com.example.bgbg.dto.user.RegisterDTO;
import com.example.bgbg.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(RegisterDTO dto);
    ResponseEntity<?> login(LoginRequestDTO dto);
    ResponseEntity<?> mypage(User user);
}
