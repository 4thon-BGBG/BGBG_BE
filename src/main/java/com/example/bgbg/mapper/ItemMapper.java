package com.example.bgbg.mapper;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.entity.Item;

public class ItemMapper {

    public static Item toEntity(ItemCreatedRequest request) {
        return Item.builder()
                .itemName(request.itemName())
                .itemCategory(request.itemCategory())
                .itemCount(request.itemCount())
                .memo(request.memo())
                .build();
    }
}
