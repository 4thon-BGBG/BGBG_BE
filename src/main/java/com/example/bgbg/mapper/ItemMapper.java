package com.example.bgbg.mapper;

import com.example.bgbg.dto.request.AiItemRequest;
import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.entity.Item;
import com.example.bgbg.entity.User;
import com.example.bgbg.shoppinglist.entity.ShoppingList;

public class ItemMapper {

    public static Item toEntity(ItemCreatedRequest request, ShoppingList shoppingList, User user) {
        return Item.builder()
                .itemName(request.itemName())
                .itemCategory(request.itemCategory())
                .itemCount(request.itemCount())
                .memo(request.memo())
                .shoppingList(shoppingList)
                .user(user)
                .build();
    }

    public static Item toEntityFromAi(AiItemRequest request, ShoppingList shoppingList, User user) {
        return Item.builder()
                .itemName(request.itemName())
                .itemCategory(request.itemCategory())
                .itemCount(1)
                .memo(null)
                .shoppingList(shoppingList)
                .user(user)
                .build();
    }
}
