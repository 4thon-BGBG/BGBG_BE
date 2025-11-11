package com.example.bgbg.item.controller;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.item.dto.request.ItemCreatedRequest;
import com.example.bgbg.item.dto.response.ItemCreatedResponse;
import com.example.bgbg.item.dto.response.ItemDetailResponse;
import com.example.bgbg.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<?> createItem(
            @RequestBody ItemCreatedRequest request,
            @AuthenticationPrincipal User loginUser
    ) {
        ItemCreatedResponse response = itemService.saveItem(loginUser, request);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_CREATE_ITEM.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_ITEM, response));
    }

    @GetMapping("/depleted")
    public ResponseEntity<?> getDepletedItems(
            @AuthenticationPrincipal User loginUser
    ) {
        List<ItemDetailResponse> items = itemService.getDepletedItems(loginUser);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_DEPLETED_ITEMS.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_DEPLETED_ITEMS, items));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItemById(
            @PathVariable Long itemId,
            @AuthenticationPrincipal User loginUser
    ) {
        ItemDetailResponse item = itemService.getItemById(loginUser, itemId);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_ITEM.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEM, item));
    }

    @GetMapping
    public ResponseEntity<?> getAllItems(
            @RequestParam(required = false) String category,
            @AuthenticationPrincipal User loginUser
    ) {
        List<ItemDetailResponse> items;
        ResponseCode responseCode;

        if (category != null && !category.isEmpty()) {
            items = itemService.getItemsByCategory(loginUser, category);
            responseCode = ResponseCode.SUCCESS_GET_CATEGORY_ITEM;
        } else {
            items = itemService.getAllItems(loginUser);
            responseCode = ResponseCode.SUCCESS_GET_ALL_ITEMS;
        }

        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_ALL_ITEMS.getStatus().value())
                .body(new ResponseDTO<>(responseCode, items));
    }
}
