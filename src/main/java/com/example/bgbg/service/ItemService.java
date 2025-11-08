package com.example.bgbg.service;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;

public interface ItemService {
    ItemCreatedResponse saveItem(ItemCreatedRequest request);
}
