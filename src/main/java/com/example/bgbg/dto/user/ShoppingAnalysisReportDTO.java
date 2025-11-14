package com.example.bgbg.dto.user;

import java.util.List;

import lombok.Builder;

@Builder
public record ShoppingAnalysisReportDTO(
        double averageCycleDays, // 평균 장보기 주기
        double averageBasketSize, // 장보기 규모
        String mostFrequentDayOfWeek,
        String mostFrequentTimeRange,
        CategorySummaryDTO topCategory,
        List<CategorySummaryDTO> categories) {}
