package com.example.bgbg.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "아이디 입력은 필수입니다.")
    private String username;

    @NotBlank(message = "패스워드 입력은 필수입니다.")
    private String password;

    @NotBlank(message = "위에 입력하신 비밀번호를 한 번 더 입력해 주세요.")
    private String confirmPassword;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;
}
