package com.example.bgbg.item.mapper;

import com.example.bgbg.item.dto.ItemCreatedRequest;
import com.example.bgbg.item.entity.Item;
import com.example.bgbg.recommend.dto.AiItemRequest;
import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.user.entity.User;

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
