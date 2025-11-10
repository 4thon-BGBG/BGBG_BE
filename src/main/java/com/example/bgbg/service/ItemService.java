package com.example.bgbg.service;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.shoppinglist.entity.ShoppingList;

public interface ItemService {
    ItemCreatedResponse saveItem(ItemCreatedRequest request);
}
