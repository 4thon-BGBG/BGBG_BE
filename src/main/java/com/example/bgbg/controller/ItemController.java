package com.example.bgbg.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.request.ItemMemoRequest;
import com.example.bgbg.dto.request.ItemSetRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.dto.response.ItemGetResponse;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Item", description = "품목 관련 API")
public class ItemController {
    private final ItemService itemService;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @Operation(summary = "새로운 품목 추가", description = "장보기 리스트에 새로운 품목 추가")
    @PostMapping("/item")
    public ResponseEntity<?> createItem(@RequestBody ItemCreatedRequest request) {
        User user = getLoggedInUser();
        ItemCreatedResponse item = itemService.saveItem(request, user);
        return ResponseEntity.status(ResponseCode.SUCCESS_CREATE_ITEM.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_ITEM, item));
    }

    @Operation(summary = "리스트 별 품목 조회", description = "특정 리스트에 추가된 품목 조회")
    @GetMapping("/item/{id}")
    public ResponseEntity<?> getItemByShoppingListId(
            @Parameter(description = "조회 할 리스트 id") @PathVariable Long id) {
        List<ItemGetResponse> items = itemService.getItemsByShoppingListId(id);
        return ResponseEntity.status(ResponseCode.SUCCESS_GET_ITEMS.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS, items));
    }

    @Operation(summary = "리스트 별 품목 카테고리 정렬 조회", description = "특정 리스트에 추가된 품목을 카테고리 순서대로 정렬하여 조회")
    @GetMapping("/item/{id}/sorted")
    public ResponseEntity<?> getItemByShoppingListIdSortedByCategory(
            @Parameter(description = "조회 할 리스트 id") @PathVariable Long id) {
        List<ItemGetResponse> items = itemService.getItemsByShoppingListIdSortedByCategory(id);
        return ResponseEntity.status(
                        ResponseCode.SUCCESS_GET_ITEMS_SORTED_BY_CATEGORY.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS_SORTED_BY_CATEGORY, items));
    }

    @Operation(summary = "품목 정보 수정", description = "리스트 ID와 품목 ID로 특정 품목의 정보 수정 (memo 제외)")
    @PatchMapping("/item")
    public ResponseEntity<?> updateItemInfo(@RequestBody ItemSetRequest request) {
        User user = getLoggedInUser();
        ItemGetResponse updatedItem = itemService.updateItemInfo(request, user);
        return ResponseEntity.status(ResponseCode.SUCCESS_UPDATE_ITEM.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_UPDATE_ITEM, updatedItem));
    }

    @Operation(summary = "품목 메모 작성", description = "리스트 ID와 품목 ID로 특정 품목의 메모 작성")
    @PatchMapping("/item/memo")
    public ResponseEntity<?> updateItemInfo(@RequestBody ItemMemoRequest request) {
        User user = getLoggedInUser();
        ItemGetResponse updatedItem = itemService.updateItemMemo(request, user);
        return ResponseEntity.status(ResponseCode.SUCCESS_UPDATE_ITEM_MEMO.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_UPDATE_ITEM_MEMO, updatedItem));
    }

    @Operation(summary = "품목 보유 여부 토글", description = "품목의 보유 여부를 토글합니다 (체크박스 기능)")
    @PatchMapping("/item/{id}/toggle")
    public ResponseEntity<?> toggleOwnItem(
            @Parameter(description = "토글할 품목 id") @PathVariable Long id) {
        User user = getLoggedInUser();
        Boolean ownItem = itemService.toggleOwnItem(id, user);
        return ResponseEntity.status(ResponseCode.SUCCESS_TOGGLE_OWN_ITEM.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_TOGGLE_OWN_ITEM, ownItem));
    }

    @Operation(summary = "품목 삭제", description = "품목 id로 품목 조회 후 품목 삭제")
    @DeleteMapping("/item/{id}")
    public ResponseEntity<?> deleteItem(
            @Parameter(description = "삭제할 품목 id") @PathVariable Long id) {
        Boolean result = itemService.deleteItemById(id);
        return ResponseEntity.status(ResponseCode.SUCCESS_DELETE_ITEM.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_DELETE_ITEM, result));
    }
}
