package com.example.bgbg.recommend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ChatRequest DTO", description = "사용자가 AI모델로 메세지를 요청보내는 요청 DTO")
public class ChatRequest {
    private String message;

    public ChatRequest(String message) {
        this.message = message;
    }
}
