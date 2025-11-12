package com.example.bgbg.item.dto.response;

import java.util.List;

public record ItemListResponse(
        List<ItemDetailResponse> items
) {
}
