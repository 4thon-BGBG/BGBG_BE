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

    SUCCESS_GET_MYPAGE(HttpStatus.OK, "마이페이지를 성공적으로 불러왔습니다."),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "유저 정보가 성공적으로 수정되었습니다."),

    /**
     * ShoppingList
     */
    SUCCESS_CREATE_LIST(HttpStatus.CREATED, "장보기 리스트 생성에 성공했습니다."),
    SUCCESS_GET_ALL_LISTS(HttpStatus.OK, "장보기 리스트 전체 조회에 성공했습니다."),
    SUCCESS_UPDATE_LIST(HttpStatus.OK, "장보기 리스트 수정에 성공했습니다."),
    SUCCESS_DELETE_LIST(HttpStatus.OK, "장보기 리스트 삭제에 성공했습니다."),

    /**
     * Item
     */
    SUCCESS_CREATE_ITEM(HttpStatus.CREATED, "품목 생성에 성공했습니다."),
    SUCCESS_GET_ITEMS(HttpStatus.OK, "품목 조회에 성공했습니다."),
    SUCCESS_UPDATE_ITEM(HttpStatus.OK, "품목 수정에 성공했습니다."),
    SUCCESS_DELETE_ITEM(HttpStatus.OK, "품목 삭제에 성공했습니다."),
    SUCCESS_UPDATE_ITEM_MEMO(HttpStatus.OK, "품목 메모 작성에 성공했습니다.");

    /**
     * Item
     */
    SUCCESS_CREATE_ITEM(HttpStatus.CREATED, "보유품목 등록에 성공했습니다."),
    SUCCESS_GET_ALL_ITEMS(HttpStatus.OK, "보유품목 전체 조회에 성공했습니다."),
    SUCCESS_GET_ITEM(HttpStatus.OK, "보유품목 상세 조회에 성공했습니다."),
    SUCCESS_GET_CATEGORY_ITEM(HttpStatus.OK, "카테고리 별 보유품목 조회에 성공했습니다."),
    SUCCESS_GET_DEPLETED_ITEMS(HttpStatus.OK, "소진된 보유품목 조회에 성공했습니다."),
    SUCCESS_UPDATE_ITEM(HttpStatus.OK, "보유품목 수정에 성공했습니다."),
    SUCCESS_DELETE_ITEM(HttpStatus.OK, "보유품목 삭제에 성공했습니다.");

    private final HttpStatus status;
    private final String message;
}
