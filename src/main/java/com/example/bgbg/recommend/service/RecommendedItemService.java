package com.example.bgbg.recommend.service;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.recommend.dto.response.IngredientResponse;
import com.example.bgbg.recommend.entity.RecommendedItem;
import com.example.bgbg.recommend.repository.RecommendedItemRepository;
import com.example.bgbg.repository.ItemRepository;
import com.example.bgbg.entity.Item;
import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.shoppinglist.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendedItemService {

    private final RecommendedItemRepository recommendedItemRepository;
    private final GptService gptService;
    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;

    // 메뉴 기반 재료 추천 및 저장 (이전 추천 삭제)
    @Transactional
    public List<IngredientResponse> getAndSaveRecommendations(String menu, User user) {
        // 이전 추천 품목 삭제
        recommendedItemRepository.deleteByUser(user);
        log.info("이전 추천 품목 삭제 완료: userId={}", user.getId());

        // AI로부터 새로운 추천 받기
        List<IngredientResponse> ingredients = gptService.getIngredients(menu);

        // 추천 품목을 RecommendedItem 테이블에 저장
        List<RecommendedItem> recommendedItems = ingredients.stream()
                .map(ingredient -> RecommendedItem.builder()
                        .itemName(ingredient.getName())
                        .itemCategory(ingredient.getCategory())
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        recommendedItemRepository.saveAll(recommendedItems);
        log.info("새로운 추천 품목 저장 완료: userId={}, count={}", user.getId(), recommendedItems.size());

        return ingredients;
    }

    // 현재 추천받은 품목 조회
    @Transactional(readOnly = true)
    public List<IngredientResponse> getCurrentRecommendations(User user) {
        List<RecommendedItem> recommendedItems = recommendedItemRepository.findByUser(user);

        return recommendedItems.stream()
                .map(item -> new IngredientResponse(item.getItemName(), item.getItemCategory()))
                .collect(Collectors.toList());
    }

    // 추천받은 품목 전체를 장보기 리스트에 추가
    @Transactional
    public void addAllToShoppingList(Long shoppingListId, User user) {
        // 장보기 리스트 조회
        ShoppingList shoppingList = shoppingListRepository
                .findById(shoppingListId)
                .orElseThrow(() -> {
                    log.warn("리스트가 존재하지 않음: listId={}", shoppingListId);
                    throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
                });

        // 추천 품목 조회
        List<RecommendedItem> recommendedItems = recommendedItemRepository.findByUser(user);

        if (recommendedItems.isEmpty()) {
            log.warn("추천받은 품목이 없음: userId={}", user.getId());
            throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
        }

        // 추천 품목을 Item으로 변환하여 저장
        List<Item> items = recommendedItems.stream()
                .map(recommendedItem -> Item.builder()
                        .itemName(recommendedItem.getItemName())
                        .itemCategory(recommendedItem.getItemCategory())
                        .itemCount(1)
                        .memo(null)
                        .shoppingList(shoppingList)
                        .user(user)
                        .build())
                .collect(Collectors.toList());

        itemRepository.saveAll(items);
        log.info("추천 품목 전체 장보기 리스트에 추가 완료: listId={}, userId={}, count={}",
                shoppingListId, user.getId(), items.size());
    }
}