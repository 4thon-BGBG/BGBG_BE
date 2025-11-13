package com.example.bgbg.recomand.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ChatResponse DTO", description = "사용자에게 답변 메세지를 응답해주는 응답 DTO")
public class ChatResponse {
    private String answer;

    public ChatResponse(String answer) {
        this.answer = answer;
    }

    public static ChatResponse of(String answer) {
        return new ChatResponse(answer);
    }

}
