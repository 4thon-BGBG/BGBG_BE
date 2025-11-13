package com.example.bgbg.exception;

import com.example.bgbg.code.ErrorCode;

public class SampleException extends GlobalException {
    public SampleException(ErrorCode code) {
        super(code);
    }
}
