package com.example.bgbg.shoppinglist.controller;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.shoppinglist.dto.request.CreateListRequest;
import com.example.bgbg.shoppinglist.dto.response.ListResponse;
import com.example.bgbg.shoppinglist.service.ShoppingListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
public class ShoppingListController {
  private final ShoppingListService shoppingListService;

  @Operation(summary = "장보기 리스트 생성", description = "장보기 리스트 화면에서 새로운 리스트 생성")
  @PostMapping
  public ResponseEntity<?> createShoppingList(@RequestBody CreateListRequest request) {
    ListResponse list = shoppingListService.createShoppingList(request);
    return ResponseEntity
        .status(ResponseCode.SUCCESS_CREATE_LIST.getStatus().value())
        .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_LIST, list));
  }

  @Operation(summary = "장보기 리스트 전체 조회", description = "장보기 리스트 화면에서 리스트의 전체 목록을 조회")
  @GetMapping
  public ResponseEntity<?> getAllShoppingLists() {
    List<ListResponse> lists = shoppingListService.getAllShoppingLists();
    return ResponseEntity
        .status(ResponseCode.SUCCESS_GET_ALL_LISTS.getStatus().value())
        .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ALL_LISTS, lists));
  }

  @Operation(summary = "장보기 리스트 삭제", description = "리스트 id로 리스트 조회 후 리스트 삭제")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteShoppingList(@Parameter(description = "특정 리스트 id")  @PathVariable Long id) {
    Boolean result = shoppingListService.deleteShoppingListById(id);
    return ResponseEntity
        .status(ResponseCode.SUCCESS_DELETE_LIST.getStatus().value())
        .body(new ResponseDTO<>(ResponseCode.SUCCESS_DELETE_LIST, result));
  }

  @Operation(summary = "장보기 리스트 이름 수정", description = "리스트 id로 리스트 조회 후 리스트 이름 수정")
  @PatchMapping("/{id}")
  public ResponseEntity<?> updateShoppingListName(@Parameter(description = "특정 리스트 id") @PathVariable Long id,
                                                  @Parameter(description = "수정할 리스트 이름") @RequestParam String listName) {
    ListResponse updatedList = shoppingListService.updateShoppingListNameById(id, listName);
    return ResponseEntity
        .status(ResponseCode.SUCCESS_UPDATE_LIST.getStatus().value())
        .body(new ResponseDTO<>(ResponseCode.SUCCESS_UPDATE_LIST, updatedList));
  }

}
