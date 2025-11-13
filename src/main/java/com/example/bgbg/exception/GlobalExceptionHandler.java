package com.example.bgbg.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.response.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /** 입력값 검증 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.BAD_REQUEST, errors));
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Object> handleGeneralException(
            GlobalException e, HttpServletRequest request) {
        ErrorResponseDTO errorReason = e.getErrorReasonHttpStatus();
        System.out.println(errorReason);
        return createErrorResponse(errorReason, request);
    }

    private ResponseEntity<Object> createErrorResponse(
            ErrorResponseDTO errorReason, HttpServletRequest request) {
        return ResponseEntity.status(errorReason.getStatus()).body(errorReason);
    }
}
