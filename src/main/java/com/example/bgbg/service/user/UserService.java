package com.example.bgbg.service.user;

import com.example.bgbg.dto.user.LoginRequestDTO;
import com.example.bgbg.dto.user.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> register(RegisterDTO dto);
    ResponseEntity<?> login(LoginRequestDTO dto);
}
