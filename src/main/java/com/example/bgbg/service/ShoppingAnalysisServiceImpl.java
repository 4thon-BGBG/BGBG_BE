package com.example.bgbg.service;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.bgbg.dto.user.ShoppingAnalysisReportDTO;
import com.example.bgbg.entity.Category;
import com.example.bgbg.entity.Item;
import com.example.bgbg.entity.User;
import com.example.bgbg.mapper.ShoppingAnalysisMapper;
import com.example.bgbg.repository.ItemRepository;
import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.shoppinglist.repository.ShoppingListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingAnalysisServiceImpl implements ShoppingAnalysisService {
    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;

    @Override
    public ShoppingAnalysisReportDTO analyze(User user) {
        // 구매된 아이템 전체 조회
        List<Item> purchase = itemRepository.findByUserIdOrderByUpdatedAtDesc(user.getId());

        if (purchase.isEmpty()) {
            return ShoppingAnalysisMapper.emptyReport();
        }

        // 평균 장보기 주기
        double avgCycleDays = calculateAverageCycleDays(purchase);

        // 평균 장보기 규모
        double avgBasketSize = calculateAverageBasketSize(user);

        // 가장 자주 장보는 요일 & 시간대
        DayOfWeek mostDayofWeekEunm = findMostFrequentDayofWeek(purchase);
        String mostDayofWeek = mostDayofWeekEunm != null ? mostDayofWeekEunm.name() : null;

        String mostTimeRange = findMostFrequentTimeRange(purchase);

        // 가장 많이 구매한 카테고리 + 카테고리별 통계
        Map<Category, Long> categoryCount =
                purchase.stream().collect(groupingBy(Item::getItemCategory, counting()));

        Category topCategory =
                categoryCount.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);

        // DTO 변환
        return ShoppingAnalysisMapper.toReport(
                avgCycleDays,
                avgBasketSize,
                mostDayofWeek,
                mostTimeRange,
                topCategory,
                categoryCount);
    }

    private double calculateAverageCycleDays(List<Item> purchase) {
        // 각각의 구매 날짜만 추출해서 정렬
        List<LocalDate> dates =
                purchase.stream()
                        .map(item -> item.getUpdatedAt().toLocalDate())
                        .distinct()
                        .sorted()
                        .toList();

        if (dates.size() < 2) {
            return 0.0;
        }

        List<Long> intervals = new ArrayList<>();
        for (int i = 1; i < dates.size(); i++) {
            long diff = ChronoUnit.DAYS.between(dates.get(i - 1), dates.get(i));
            intervals.add(diff);
        }

        return intervals.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    private double calculateAverageBasketSize(User user) {
        List<ShoppingList> lists = shoppingListRepository.findByUser((user));

        if (lists.isEmpty()) {
            return 0.0;
        }

        return lists.stream()
                .mapToInt(list -> list.getItems().stream().mapToInt(Item::getItemCount).sum())
                .average()
                .orElse(0.0);
    }

    private DayOfWeek findMostFrequentDayofWeek(List<Item> purchase) {
        Map<DayOfWeek, Long> dayCount =
                purchase.stream()
                        .collect(
                                groupingBy(item -> item.getUpdatedAt().getDayOfWeek(), counting()));

        return dayCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private String findMostFrequentTimeRange(List<Item> purchase) {
        Map<String, Long> timeRangeCount =
                purchase.stream()
                        .collect(groupingBy(item -> toTimeRange(item.getUpdatedAt()), counting()));

        return timeRangeCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private String toTimeRange(LocalDateTime date) {
        int hour = date.getHour();
        if (hour >= 6 && hour <= 12) {
            return "오전";
        } else if (hour >= 12 && hour < 18) {
            return "오후";
        } else if (hour >= 18 && hour < 23) {
            return "저녁";
        } else {
            return "새벽";
        }
    }
}
