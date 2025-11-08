package com.example.bgbg.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ResponseCode {
    /**
     * User
     */
    SUCCESS_REGISTER(HttpStatus.CREATED, "회원가입을 성공했습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "로그인을 성공했습니다."),

    SUCCESS_GET_MYPAGE(HttpStatus.OK, "마이페이지를 성공적으로 불러왔습니다.");




    private final HttpStatus status;
    private final String message;
}
