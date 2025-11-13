package com.example.bgbg.dto.response;

import java.util.Map;

import com.example.bgbg.code.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponseDTO {
    private Boolean isSuccess;
    private int status;
    private String error;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> errors;

    public ErrorResponseDTO(ErrorCode errorCode, Map<String, String> errors) {
        this.isSuccess = false;
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.message = errorCode.getMessage();
        this.errors = errors;
    }
}
