package com.example.bgbg.service;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.dto.response.ItemGetResponse;

import java.util.List;

public interface ItemService {
    ItemCreatedResponse saveItem(ItemCreatedRequest request);
    List<ItemGetResponse> getItemsByShoppingListId(Long shoppingListId);
}
