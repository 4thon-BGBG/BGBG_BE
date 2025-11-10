package com.example.bgbg.service;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.request.ItemCreatedRequest;
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
        shoppingList.setUser(user);

        Item item = ItemMapper.toEntity(request, shoppingList);

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

}
