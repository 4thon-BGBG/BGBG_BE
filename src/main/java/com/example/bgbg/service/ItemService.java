package com.example.bgbg.service;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.request.ItemMemoRequest;
import com.example.bgbg.dto.request.ItemSetRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.dto.response.ItemGetResponse;
import com.example.bgbg.entity.User;

import java.util.List;

public interface ItemService {
    ItemCreatedResponse saveItem(ItemCreatedRequest request, User user);
    List<ItemGetResponse> getItemsByShoppingListId(Long shoppingListId);
    ItemGetResponse updateItemInfo(ItemSetRequest request, User user);
    ItemGetResponse updateItemMemo(ItemMemoRequest request, User user);
    Boolean deleteItemById(Long id);
}
