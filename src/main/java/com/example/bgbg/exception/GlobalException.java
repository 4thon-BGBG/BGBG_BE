package com.example.bgbg.exception;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.response.ErrorResponseDTO;

public class GlobalException extends RuntimeException {
    private ErrorCode code;

    public GlobalException(ErrorCode code) {
        this.code = code;
    }

    public ErrorResponseDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}