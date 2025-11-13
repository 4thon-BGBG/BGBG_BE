package com.example.bgbg.service;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.request.ItemMemoRequest;
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

        // 품목 이름 중복 체크
        if (itemRepository.existsByItemName(request.itemName())) {
            log.warn("중복된 품목 이름: {}", request.itemName());
            throw new GlobalException(ErrorCode.DUPLICATE_ITEM_NAME);
        }

        ShoppingList shoppingList = null;

        // shoppingListId가 제공된 경우에만 ShoppingList 조회
        if (request.shoppingListId() != null) {
            shoppingList = shoppingListRepository.findById(request.shoppingListId())
                .orElseThrow(() -> {
                    log.warn("리스트가 존재하지 않음");
                    throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
                });
        }

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
                    .itemId(item.getId())
                    .itemName(item.getItemName())
                    .itemCount(item.getItemCount())
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
    @Transactional(readOnly = true)
    public List<ItemGetResponse> getItemsByShoppingListIdSortedByCategory(Long shoppingListId) {
        try {
            List<Item> items = itemRepository.findByShoppingListId(shoppingListId);

            return items.stream()
                .sorted((item1, item2) -> {
                    // Category enum의 ordinal() 값으로 정렬 (선언 순서대로)
                    return Integer.compare(
                        item1.getItemCategory().ordinal(),
                        item2.getItemCategory().ordinal()
                    );
                })
                .map(item -> ItemGetResponse.builder()
                    .itemId(item.getId())
                    .itemName(item.getItemName())
                    .itemCount(item.getItemCount())
                    .category(item.getItemCategory())
                    .memo(item.getMemo())
                    .build())
                .toList();

        } catch (Exception e) {
            log.error("품목 카테고리별 정렬 조회 실패", e);
            throw new GlobalException(ErrorCode.ITEM_GET_FAILED);
        }
    }

    @Override
    @Transactional
    public ItemGetResponse updateItemInfo(ItemSetRequest request, User user) {
        // 리스트 안에 담긴 품목인지 체크
        if (!itemRepository.isItemInShoppingList(request.getItemId())) {

            // 품목 이름 중복 체크
            if (itemRepository.existsByItemName(request.getItemName())) {
                log.warn("중복된 품목 이름: {}", request.getItemName());
                throw new GlobalException(ErrorCode.DUPLICATE_ITEM_NAME);
            }

            Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> {
                    log.warn("품목이 존재하지 않음");
                    throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
                });

            // 품목 정보 수정 (memo 제외)
            item.updateItemInfo(
                request.getItemName(),
                request.getItemCount(),
                request.getCategory()
            );

            Item updatedItem = itemRepository.save(item);

            return ItemGetResponse.builder()
                .itemId(updatedItem.getId())
                .itemName(updatedItem.getItemName())
                .itemCount(updatedItem.getItemCount())
                .category(updatedItem.getItemCategory())
                .memo(updatedItem.getMemo())
                .build();

        } else {
            ShoppingList shoppingList = shoppingListRepository.findById(request.getShoppingListId())
                .orElseThrow(() -> {
                    log.warn("리스트가 존재하지 않음");
                    throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
                });

            // 품목 이름 중복 체크
            if (itemRepository.existsByItemName(request.getItemName())) {
                log.warn("중복된 품목 이름: {}", request.getItemName());
                throw new GlobalException(ErrorCode.DUPLICATE_ITEM_NAME);
            }

            Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> {
                    log.warn("품목이 존재하지 않음");
                    throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
                });

            // 품목 정보 수정 (memo 제외)
            item.updateItemInfo(
                request.getItemName(),
                request.getItemCount(),
                request.getCategory()
            );

            Item updatedItem = itemRepository.save(item);

            return ItemGetResponse.builder()
                .itemId(updatedItem.getId())
                .itemName(updatedItem.getItemName())
                .itemCount(updatedItem.getItemCount())
                .category(updatedItem.getItemCategory())
                .memo(updatedItem.getMemo())
                .build();
        }


    }

    @Override
    @Transactional
    public ItemGetResponse updateItemMemo(ItemMemoRequest request, User user) {
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

        item.updateItemMemo(request.getMemo());

        Item updatedItem = itemRepository.save(item);

        return ItemGetResponse.builder()
            .itemId(updatedItem.getId())
            .itemName(updatedItem.getItemName())
            .itemCount(updatedItem.getItemCount())
            .category(updatedItem.getItemCategory())
            .memo(updatedItem.getMemo())
            .build();
    }

    @Override
    @Transactional
    public Boolean toggleOwnItem(Long itemId, User user) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> {
                log.warn("품목이 존재하지 않음");
                throw new GlobalException(ErrorCode.ITEM_NOT_FOUND);
            });

        // 해당 품목이 해당 사용자의 품목인지 검증
        if (!item.getUser().getId().equals(user.getId())) {
            log.warn("해당 사용자의 품목이 아님");
            throw new GlobalException(ErrorCode.UNAUTHORIZED_UESR);
        }

        item.toggleOwnItem();
        itemRepository.save(item);

        log.info("품목 보유 여부 토글 완료: itemId={}, ownItem={}", itemId, item.getOwnItem());
        return item.getOwnItem();
    }

    @Override
    @Transactional
    public Boolean deleteItemById(Long id) {
        log.info("품목 삭제 시도 : id={}", id);

        Item item = itemRepository.findById(id)
            .orElseThrow(() -> {
                log.warn("품목이 존재하지 않음");
                throw new GlobalException(ErrorCode.ITEM_GET_FAILED);
            });

        itemRepository.deleteById(id);

        log.info("품목 삭제 완료 : id={}", id);
        return true;
    }

}
