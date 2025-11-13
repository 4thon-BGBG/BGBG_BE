package com.example.bgbg.service.user;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.example.bgbg.dto.user.LoginRequestDTO;
import com.example.bgbg.dto.user.MyHistoryDTO;
import com.example.bgbg.dto.user.RegisterDTO;
import com.example.bgbg.dto.user.UserUpdateDTO;
import com.example.bgbg.entity.User;

public interface UserService {
    ResponseEntity<?> register(RegisterDTO dto);

    ResponseEntity<?> login(LoginRequestDTO dto);

    ResponseEntity<?> mypage(User user);

    ResponseEntity<?> updateUser(Long loginUserId, UserUpdateDTO dto);

    Page<MyHistoryDTO> getMyHistory(User user, int page, int size);
}
