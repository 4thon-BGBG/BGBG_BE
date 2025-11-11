package com.example.bgbg.dto.request;

import com.example.bgbg.entity.Category;

public record ItemCreatedRequest(
    String itemName,
    int itemCount,
    Category itemCategory,
    String memo,
    Long shoppingListId
) {

}
