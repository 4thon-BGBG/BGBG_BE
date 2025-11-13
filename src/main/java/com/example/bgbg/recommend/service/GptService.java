package com.example.bgbg.recommend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bgbg.entity.Category;
import com.example.bgbg.recommend.OpenAiClient;
import com.example.bgbg.recommend.dto.response.GptResponse;
import com.example.bgbg.recommend.dto.response.IngredientResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GptService {

    private final OpenAiClient openAiClient;

    // 클라이언트의 질문 받아 재료 리스트로 반환
    public List<IngredientResponse> getIngredients(String menu) {
        // 질문을 openaiClient를 통해 openai로 전달
        GptResponse response = openAiClient.getChatCompletion(menu);

        // 응답 중 첫번 째 메세지의 content 추출
        String content = response.getChoices().get(0).getMessage().getContent();

        // content를 파싱해서 IngredientResponse 리스트로 변환
        return parseIngredients(content);
    }

    // GPT 응답 텍스트를 파싱해서 IngredientResponse 리스트로 변환
    private List<IngredientResponse> parseIngredients(String content) {
        List<IngredientResponse> ingredients = new ArrayList<>();

        log.info("GPT 응답 content: {}", content);

        // 쉼표로 분리 (GPT가 한 줄로 응답하는 경우)
        String[] items = content.split(",");
        log.info("분리된 아이템 개수: {}", items.length);

        for (String item : items) {
            item = item.trim();
            log.info("처리 중인 아이템: '{}'", item);

            if (item.isEmpty()) {
                continue;
            }

            // "재료이름: 카테고리" 형식으로 파싱 (첫 번째 콜론으로만 분리)
            int colonIndex = item.indexOf(":");
            if (colonIndex > 0) {
                String name = item.substring(0, colonIndex).trim();
                String categoryStr = item.substring(colonIndex + 1).trim();

                try {
                    // String을 Category enum으로 변환
                    Category category = Category.valueOf(categoryStr);
                    log.info("파싱된 재료: name={}, category={}", name, category);
                    ingredients.add(new IngredientResponse(name, category));
                } catch (IllegalArgumentException e) {
                    log.warn("유효하지 않은 카테고리: {}. 해당 재료를 건너뜁니다.", categoryStr);
                }
            }
        }

        log.info("최종 파싱된 재료 개수: {}", ingredients.size());
        return ingredients;
    }
}
