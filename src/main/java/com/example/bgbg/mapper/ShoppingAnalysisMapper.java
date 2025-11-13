package com.example.bgbg.mapper;

import java.util.List;
import java.util.Map;

import com.example.bgbg.dto.user.CategorySummaryDTO;
import com.example.bgbg.dto.user.ShoppingAnalysisReportDTO;
import com.example.bgbg.entity.Category;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShoppingAnalysisMapper {
    public static ShoppingAnalysisReportDTO toReport(
            double averageCycleDays,
            double averageBasketSize,
            String mostFrequentDayOfWeek,
            String mostFrequentTimeRange,
            Category topCategory,
            Map<Category, Long> categoryCountMap) {
        // 카테고리별 횟수를 List로 변환
        List<CategorySummaryDTO> categorySummaries =
                categoryCountMap.entrySet().stream()
                        .map(
                                entry ->
                                        CategorySummaryDTO.builder()
                                                .category(entry.getKey().name())
                                                .count(entry.getValue())
                                                .build())
                        .toList();

        // 가장 많이 구매한 topCategory 정보를 CategorySummaryDTO 형태로 변환
        CategorySummaryDTO topCategoryDto = null;
        if (topCategory != null) {
            long count = categoryCountMap.getOrDefault(topCategory, 0L);
            topCategoryDto =
                    CategorySummaryDTO.builder().category(topCategory.name()).count(count).build();
        }

        // 모든 분석 결과를 DTO 객체로 통합해 반환
        return ShoppingAnalysisReportDTO.builder()
                .averageCycleDays(averageCycleDays)
                .averageBasketSize(averageBasketSize)
                .mostFrequentDayOfWeek(mostFrequentDayOfWeek)
                .mostFrequentTimeRange(mostFrequentTimeRange)
                .topCategory(topCategoryDto)
                .categories(categorySummaries)
                .build();
    }

    public static ShoppingAnalysisReportDTO emptyReport() {
        // 데이터가 없을 때 기본 값
        return ShoppingAnalysisReportDTO.builder()
                .averageCycleDays(0.0)
                .averageBasketSize(0.0)
                .mostFrequentDayOfWeek(null)
                .mostFrequentTimeRange(null)
                .topCategory(null)
                .categories(List.of())
                .build();
    }
}
