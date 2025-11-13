package com.example.bgbg.code;

import org.springframework.http.HttpStatus;

import com.example.bgbg.dto.response.ErrorResponseDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    /** 400 BAD_REQUEST - 잘못된 요청 */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다. 다시 확인해 주세요."),

    /** 401 UNAUTHORIZED - 인증 실패 */
    PASSWORD_NOT_CORRECT(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_UESR(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    /** 403 FORBIDDEN - 권한 없음 */
    OWN_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 품목에 접근할 권한이 없습니다."),

    /** 404 NOT_FOUND - 요청한 리소스를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 아이디를 가진 사용자가 존재하지 않습니다."),
    LIST_NOT_FOUND(HttpStatus.NOT_FOUND, "장보기 리스트를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 품목을 찾을 수 없습니다."),
    OWN_NOT_FOUND(HttpStatus.NOT_FOUND, "보유 품목을 찾을 수 없습니다."),

    /** 406 NOT_ACCEPTABLE - 허용되지 않는 요청 형식 */

    /** 409 CONFLICT - 요청 충돌 */
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    DUPLICATE_ITEM_NAME(HttpStatus.CONFLICT, "이미 존재하는 품목 이름입니다."),

    /** 500 INTERNAL_SERVER_ERROR - 서버 내부 오류 */
    LIST_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 리스트 생성에 실패했습니다."),
    LIST_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 리스트 수정에 실패했습니다."),
    LIST_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "장보기 리스트 삭제에 실패했습니다."),
    ITEM_CREATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "품목 생성에 실패했습니다."),
    ITEM_GET_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "품목 조회에 실패했습니다."),

    /** 502 BAD_GATEWAY - 이트웨이 또는 프록시 서버 오류 */
    SAMPLE_EXCEPTION(HttpStatus.BAD_REQUEST, "샘플 예외입니다.");

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
