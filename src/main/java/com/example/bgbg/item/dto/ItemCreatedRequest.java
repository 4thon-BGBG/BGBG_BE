package com.example.bgbg.item.dto;

import com.example.bgbg.common.entity.Category;

public record ItemCreatedRequest(
        String itemName, int itemCount, Category itemCategory, String memo, Long shoppingListId) {}
