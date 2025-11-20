package com.example.bgbg.user.dto;

import lombok.Builder;

@Builder
public record CategorySummaryDTO(
        String category, // 카테고리
        long count // 구매 횟수
        ) {}
