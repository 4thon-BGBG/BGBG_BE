package com.example.bgbg.item.dto.response;

import lombok.Builder;

@Builder
public record ItemDetailResponse(
        Long userId,
        Long itemId,
        String itemName,
        int itemCount,
        String itemCategory
) {
}
