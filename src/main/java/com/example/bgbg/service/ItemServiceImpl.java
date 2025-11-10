package com.example.bgbg.service;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.request.ItemSetRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.dto.response.ItemGetResponse;
import com.example.bgbg.entity.Item;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.mapper.ItemMapper;
import com.example.bgbg.repository.ItemRepository;
import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.shoppinglist.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;

    @Override
    @Transactional
    public ItemCreatedResponse saveItem(ItemCreatedRequest request, User user) {

      ShoppingList shoppingList = shoppingListRepository.findById(request.shoppingListId())
          .orElseThrow(() -> {
            log.warn("리스트가 존재하지 않음");
            throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
          });

        Item item = ItemMapper.toEntity(request, shoppingList, user);

        Item savedItem = itemRepository.save(item);

        return new ItemCreatedResponse(savedItem.getId(), "item 등록 완료");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemGetResponse> getItemsByShoppingListId(Long shoppingListId) {
        try {
            List<Item> items = itemRepository.findByShoppingListId(shoppingListId);

            return items.stream()
                .map(item -> ItemGetResponse.builder()
                    .listName(item.getShoppingList().getListName())
                    .itemName(item.getItemName())
                    .itemCount(String.valueOf(item.getItemCount()))
                    .category(item.getItemCategory())
                    .memo(item.getMemo())
                    .build())
                .toList();

        } catch (Exception e) {
            log.error("품목 조회 실패", e);
            throw new GlobalException(ErrorCode.ITEM_GET_FAILED);
        }
    }

    @Override
    @Transactional
    public ItemGetResponse updateItemInfo(ItemSetRequest request, User user) {
        ShoppingList shoppingList = shoppingListRepository.findById(request.getShoppingListId())
            .orElseThrow(() -> {
                log.warn("리스트가 존재하지 않음");
                throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
            });

        Item item = itemRepository.findById(request.getItemId())
            .orElseThrow(() -> {
                log.warn("품목이 존재하지 않음");
                throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
            });

        // 해당 품목이 해당 리스트에 속하는지 검증
        if (!item.getShoppingList().getId().equals(shoppingList.getId())) {
            log.warn("해당 리스트에 속한 품목이 아님");
            throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
        }

        // 품목 정보 수정 (memo 제외)
        item.updateItemInfo(
            request.getItemName(),
            request.getItemCount(),
            request.getCategory()
        );

        Item updatedItem = itemRepository.save(item);

        return ItemGetResponse.builder()
            .listName(shoppingList.getListName())
            .itemName(updatedItem.getItemName())
            .itemCount(String.valueOf(updatedItem.getItemCount()))
            .category(updatedItem.getItemCategory())
            .memo(updatedItem.getMemo())
            .build();
    }

    @Transactional
    public Boolean deleteItemById(Long id) {

    }

}
