package com.example.bgbg.item.service;

import com.example.bgbg.entity.User;
import com.example.bgbg.item.dto.request.ItemCreatedRequest;
import com.example.bgbg.item.dto.response.ItemCreatedResponse;
import com.example.bgbg.item.dto.response.ItemDetailResponse;

import java.util.List;

public interface ItemService {
    ItemCreatedResponse saveItem(User user, ItemCreatedRequest request);
    ItemDetailResponse getItemById(User user, Long itemId);
    List<ItemDetailResponse> getAllItems(User user);
    List<ItemDetailResponse> getItemsByCategory(User user, String category);
    List<ItemDetailResponse> getDepletedItems(User user);
}
