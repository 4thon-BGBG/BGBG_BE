package com.example.bgbg.recommend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.bgbg.entity.Category;
import com.example.bgbg.own.dto.response.OwnDetailResponse;
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

    // 보유 품목 기반 메뉴 추천
    public List<String> getMenuRecommendations(List<OwnDetailResponse> ownItems) {
        // OwnDetailResponse 리스트를 문자열로 변환
        String ownItemsText = buildOwnItemsPrompt(ownItems);

        log.info("보유 품목 프롬프트: {}", ownItemsText);

        // OpenAI API 호출
        GptResponse response = openAiClient.getMenuRecommendation(ownItemsText);

        // 응답에서 content 추출
        String content = response.getChoices().get(0).getMessage().getContent();

        log.info("메뉴 추천 응답: {}", content);

        // 응답을 파싱해서 메뉴 리스트로 변환
        return parseMenus(content);
    }

    // OwnDetailResponse 리스트를 프롬프트 문자열로 변환
    private String buildOwnItemsPrompt(List<OwnDetailResponse> ownItems) {
        if (ownItems == null || ownItems.isEmpty()) {
            return "현재 보유 중인 재료가 없습니다. 일반적인 가정식 메뉴 3가지를 추천해주세요.";
        }

        String itemsList = ownItems.stream()
                .map(own -> String.format("%s %d개", own.ownName(), own.ownCount()))
                .collect(Collectors.joining(", "));

        return String.format("현재 보유 중인 재료: %s. 이 재료들로 만들 수 있는 메뉴를 추천해주세요.", itemsList);
    }

    // GPT 응답 텍스트를 파싱해서 메뉴 리스트로 변환
    private List<String> parseMenus(String content) {
        // 쉼표로 분리
        List<String> menus = Arrays.stream(content.split(","))
                .map(String::trim)
                .filter(menu -> !menu.isEmpty())
                .collect(Collectors.toList());

        log.info("파싱된 메뉴 개수: {}", menus.size());
        return menus;
    }
}
