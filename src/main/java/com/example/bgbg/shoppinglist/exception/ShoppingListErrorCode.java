package com.example.bgbg.shoppinglist.exception;

import com.example.bgbg.dto.response.ErrorResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ShoppingListErrorCode {
    /**
     * 404 NOT_FOUND - 요청한 리소스를 찾을 수 없음
     */
    LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "장보기 리스트를 찾을 수 없습니다."),

    /**
     * 500 INTERNAL_SERVER_ERROR - 서버 내부 오류
     */
    LIST_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 리스트 생성에 실패했습니다."),
    LIST_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 리스트 수정에 실패했습니다."),
    LIST_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 리스트 삭제에 실패했습니다.");

    private final HttpStatus status;
    private final String message;

    public ErrorResponseDTO getReasonHttpStatus() {
        return ErrorResponseDTO.builder()
                .message(message)
                .status(status.value())
                .isSuccess(false)
                .error(this.name())
                .build();
    }
}
