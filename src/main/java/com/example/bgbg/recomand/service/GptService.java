package com.example.bgbg.recomand.service;

import com.example.bgbg.recomand.OpenAiClient;
import com.example.bgbg.recomand.dto.response.GptResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GptService {

    private final OpenAiClient openAiClient;

    // 클라이언트의 질문 받아 openai 응답 텍스트 받환
    public String getAnswer(String question) {
        // 질문을 openaiClient를 통해 openai로 전달
        GptResponse response = openAiClient.getChatCompletion(question);

        // 응답 중 첫번 째 메세지의 content 추출
        return response.getChoices().get(0).getMessage().getContent();
    }

}
