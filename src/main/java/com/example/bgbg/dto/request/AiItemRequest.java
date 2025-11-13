package com.example.bgbg.dto.request;

import com.example.bgbg.entity.Category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "AiItemRequest", description = "AI 추천 재료를 품목으로 추가하는 요청 DTO")
public record AiItemRequest(
        @Schema(description = "품목 이름") String itemName,
        @Schema(description = "품목 카테고리") Category itemCategory,
        @Schema(description = "장보기 리스트 ID") Long shoppingListId) {}
