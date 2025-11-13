package com.example.bgbg.service;

import java.util.List;

import com.example.bgbg.dto.request.AiItemRequest;
import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.request.ItemMemoRequest;
import com.example.bgbg.dto.request.ItemSetRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.dto.response.ItemGetResponse;
import com.example.bgbg.entity.User;

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
