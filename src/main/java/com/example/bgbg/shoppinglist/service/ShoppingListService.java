package com.example.bgbg.shoppinglist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bgbg.code.ErrorCode;
import com.example.bgbg.dto.response.ItemGetResponse;
import com.example.bgbg.entity.User;
import com.example.bgbg.exception.GlobalException;
import com.example.bgbg.shoppinglist.dto.request.CreateListRequest;
import com.example.bgbg.shoppinglist.dto.response.ListItemResponse;
import com.example.bgbg.shoppinglist.dto.response.ListResponse;
import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.shoppinglist.mapper.ShoppingListMapper;
import com.example.bgbg.shoppinglist.repository.ShoppingListRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;

    @Transactional
    public ListResponse createShoppingList(CreateListRequest request, User user) {

        try {
            ShoppingList shoppingList =
                    ShoppingList.builder().listName(request.getListName()).build();
            shoppingList.setUser(user);
            log.info("새로운 리스트 생성 완료");

            ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
            log.info("새로운 리스트 저장 완료");

            return shoppingListMapper.toListResponse(savedShoppingList);

        } catch (Exception e) {
            log.error("장보기 리스트 생성 실패", e);
            throw new GlobalException(ErrorCode.LIST_CREATE_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public List<ListResponse> getAllShoppingLists() {

        try {
            List<ShoppingList> shoppingLists = shoppingListRepository.findAll();
            log.info("장보기 리스트 전체 조회 완료");

            return shoppingLists.stream().map(shoppingListMapper::toListResponse).toList();

        } catch (Exception e) {
            log.error("장보기 리스트 전체 조회 실패", e);
            throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public List<ListItemResponse> getAllListItems(User user) {
        try {
            List<ShoppingList> shoppingLists = shoppingListRepository.findByUser(user);
            log.info("사용자별 장보기 리스트와 품목 전체 조회 완료: userId={}", user.getId());

            return shoppingLists.stream()
                    .map(
                            shoppingList -> {
                                List<ItemGetResponse> items =
                                        shoppingList.getItems().stream()
                                                .map(
                                                        item ->
                                                                ItemGetResponse.builder()
                                                                        .listName(
                                                                                shoppingList
                                                                                        .getListName())
                                                                        .itemId(item.getId())
                                                                        .itemName(
                                                                                item.getItemName())
                                                                        .itemCount(
                                                                                item.getItemCount())
                                                                        .category(
                                                                                item
                                                                                        .getItemCategory())
                                                                    .ownItem(item.getOwnItem())
                                                                        .memo(item.getMemo())
                                                                        .build())
                                                .toList();

                                return ListItemResponse.builder()
                                        .listId(shoppingList.getId())
                                        .listName(shoppingList.getListName())
                                        .items(items)
                                        .build();
                            })
                    .toList();

        } catch (Exception e) {
            log.error("장보기 리스트와 품목 전체 조회 실패", e);
            throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
        }
    }

    @Transactional
    public ListResponse updateShoppingListNameById(Long id, String listName) {

        ShoppingList shoppingList =
                shoppingListRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    log.warn("리스트가 존재하지 않음");
                                    throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
                                });

        shoppingList.updateName(listName);
        shoppingListRepository.save(shoppingList);

        return shoppingListMapper.toListResponse(shoppingList);
    }

    @Transactional
    public Boolean deleteShoppingListById(Long id) {
        log.info("리스트 삭제 시도 : id={}", id);

        ShoppingList shoppingList =
                shoppingListRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    log.warn("리스트가 존재하지 않음");
                                    throw new GlobalException(ErrorCode.LIST_NOT_FOUND);
                                });

        shoppingListRepository.deleteById(id);

        log.info("리스트 삭제 완료 : id={}", id);
        return true;
    }
}
