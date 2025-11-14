package com.example.bgbg.shoppinglist.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.shoppinglist.dto.request.CreateListRequest;
import com.example.bgbg.shoppinglist.dto.response.ListItemResponse;
import com.example.bgbg.shoppinglist.dto.response.ListResponse;
import com.example.bgbg.shoppinglist.service.ShoppingListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
@Tag(name = "ShoppingList", description = "장보기 리스트 관련 API")
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @Operation(summary = "장보기 리스트 생성", description = "장보기 리스트 화면에서 새로운 리스트 생성")
    @PostMapping
    public ResponseEntity<?> createShoppingList(@RequestBody CreateListRequest request) {
        User user = getLoggedInUser();
        ListResponse list = shoppingListService.createShoppingList(request, user);
        return ResponseEntity.status(ResponseCode.SUCCESS_CREATE_LIST.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_LIST, list));
    }

    @Operation(summary = "장보기 리스트 전체 조회", description = "Ai 추천 품목 리스트에 품목 추가 시 리스트 선택 창에서 사용")
    @GetMapping
    public ResponseEntity<?> getAllShoppingLists() {
        List<ListResponse> lists = shoppingListService.getAllShoppingLists();
        return ResponseEntity.status(ResponseCode.SUCCESS_GET_ALL_LISTS.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ALL_LISTS, lists));
    }

    @Operation(
            summary = "장보기 리스트와 품목 전체 조회",
            description = "로그인한 사용자의 모든 장보기 리스트와 각 리스트에 담긴 품목들을 조회")
    @GetMapping("/items")
    public ResponseEntity<?> getAllListItems() {
        User user = getLoggedInUser();
        List<ListItemResponse> listItems = shoppingListService.getAllListItems(user);
        return ResponseEntity.status(ResponseCode.SUCCESS_GET_ALL_LIST_ITEMS.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ALL_LIST_ITEMS, listItems));
    }

    @Operation(summary = "장보기 리스트 삭제", description = "리스트 id로 리스트 조회 후 리스트 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShoppingList(
            @Parameter(description = "특정 리스트 id") @PathVariable Long id) {
        Boolean result = shoppingListService.deleteShoppingListById(id);
        return ResponseEntity.status(ResponseCode.SUCCESS_DELETE_LIST.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_DELETE_LIST, result));
    }

    @Operation(summary = "장보기 리스트 이름 수정", description = "리스트 id로 리스트 조회 후 리스트 이름 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateShoppingListName(
            @Parameter(description = "특정 리스트 id") @PathVariable Long id,
            @Parameter(description = "수정할 리스트 이름") @RequestParam String listName) {
        ListResponse updatedList = shoppingListService.updateShoppingListNameById(id, listName);
        return ResponseEntity.status(ResponseCode.SUCCESS_UPDATE_LIST.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_UPDATE_LIST, updatedList));
    }
}
