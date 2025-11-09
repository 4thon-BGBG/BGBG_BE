package com.example.bgbg.service.user;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ErrorResponseDTO;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.dto.user.LoginRequestDTO;
import com.example.bgbg.dto.user.MyPageDTO;
import com.example.bgbg.dto.user.RegisterDTO;
import com.example.bgbg.dto.user.UserResponseDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.jwt.JWTUtil;
import com.example.bgbg.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    // 회원가입
    @Override
    public ResponseEntity<?> register(RegisterDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        // Password != confirmPassword
        if(!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseEntity.status(ErrorCode.PASSWORD_MISMATCH.getStatus().value())
                    .body(new ErrorResponseDTO(ErrorCode.PASSWORD_MISMATCH, null));
        }

        // 이미 존재하는 아이디일 경우
        if(userRepository.existsByUsername(dto.getUsername())) {
            return ResponseEntity.status(ErrorCode.DUPLICATE_EMAIL.getStatus().value())
                    .body(new ErrorResponseDTO(ErrorCode.DUPLICATE_EMAIL, null));
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .username(dto.getUsername())
                .password(encodedPassword)
                .nickname(dto.getNickname())
                .build();

        userRepository.save(user);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_REGISTER.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_REGISTER, dto));
    }

    // 로그인
    @Override
    @Transactional
    public ResponseEntity<?> login(LoginRequestDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if(!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity
                    .status(ErrorCode.PASSWORD_NOT_CORRECT.getStatus().value())
                    .body(new ErrorResponseDTO(ErrorCode.PASSWORD_NOT_CORRECT, null));
        }

        String token = jwtUtil.createJWT(user.getUsername());

        UserResponseDTO resp = UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .token(token)
                .build();

        return ResponseEntity
                .status(ResponseCode.SUCCESS_LOGIN.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_LOGIN, resp));
    }

    // mypage
    @Override
    public ResponseEntity<?> mypage(User user) {

        MyPageDTO dto = MyPageDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_MYPAGE.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_MYPAGE, dto));
    }


}
