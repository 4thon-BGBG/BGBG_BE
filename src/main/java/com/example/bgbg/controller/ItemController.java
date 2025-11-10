package com.example.bgbg.controller;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.dto.response.ItemGetResponse;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.entity.Category;
import com.example.bgbg.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Item", description = "품목 관련 API")
public class ItemController {
    private final ItemService itemService;

    @Operation(summary = "새로운 품목 추가", description = "장보기 리스트에 새로운 품목 추가")
    @PostMapping("/item")
    public ResponseEntity<?> createItem(@RequestBody ItemCreatedRequest request) {
        ItemCreatedResponse item = itemService.saveItem(request);
        return ResponseEntity
            .status(ResponseCode.SUCCESS_CREATE_ITEM.getStatus().value())
            .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_ITEM, item));
    }

    @Operation(summary = "리스트 별 품목 조회", description = "특정 리스트에 추가된 품목 조회")
    @GetMapping("/item/{id}")
    public ResponseEntity<?> getItemByShoppingListId(@Parameter(description = "조회 할 리스트 id")
                                                       @PathVariable Long id) {
        List<ItemGetResponse> items = itemService.getItemsByShoppingListId(id);
        return ResponseEntity
            .status(ResponseCode.SUCCESS_GET_ITEMS.getStatus().value())
            .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS, items));
    }
}
