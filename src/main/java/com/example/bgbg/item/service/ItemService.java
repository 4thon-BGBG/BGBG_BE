package com.example.bgbg.item.service;

import java.util.List;

import com.example.bgbg.item.dto.ItemCreatedRequest;
import com.example.bgbg.item.dto.ItemCreatedResponse;
import com.example.bgbg.item.dto.ItemGetResponse;
import com.example.bgbg.item.dto.ItemMemoRequest;
import com.example.bgbg.item.dto.ItemSetRequest;
import com.example.bgbg.recommend.dto.AiItemRequest;
import com.example.bgbg.user.entity.User;

public interface ItemService {
    ItemCreatedResponse saveItem(ItemCreatedRequest request, User user);

    ItemCreatedResponse saveItemFromAi(AiItemRequest request, User user);

    List<ItemGetResponse> getItemsByShoppingListId(Long shoppingListId);

    List<ItemGetResponse> getItemsByShoppingListIdSortedByCategory(Long shoppingListId);

    ItemGetResponse updateItemInfo(ItemSetRequest request, User user);

    ItemGetResponse updateItemMemo(ItemMemoRequest request, User user);

    Boolean toggleOwnItem(Long itemId, User user);

    Boolean deleteItemById(Long id);
}
