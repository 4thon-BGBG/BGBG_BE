package com.example.bgbg.user.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.bgbg.user.dto.LoginRequestDTO;
import com.example.bgbg.user.dto.MyHistoryDTO;
import com.example.bgbg.user.dto.RegisterDTO;
import com.example.bgbg.user.dto.UserUpdateDTO;
import com.example.bgbg.user.entity.User;

public interface UserService {
    ResponseEntity<?> register(RegisterDTO dto);

    ResponseEntity<?> login(LoginRequestDTO dto);

    ResponseEntity<?> mypage(User user);

    ResponseEntity<?> updateUser(Long loginUserId, UserUpdateDTO dto);

    Page<MyHistoryDTO> getMyHistory(User user, int page, int size);
}
