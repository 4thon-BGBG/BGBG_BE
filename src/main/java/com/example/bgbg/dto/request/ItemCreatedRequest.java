package com.example.bgbg.dto.request;

public record ItemCreatedRequest(
    String itemName,
    int itemCount,
    String itemCategory,
    String memo,
    Long shoppingListId
) {

}
