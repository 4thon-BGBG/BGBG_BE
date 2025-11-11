package com.example.bgbg.item.dto.request;

public record ItemCreatedRequest(
    String itemName,
    int itemCount,
    String itemCategory
) {

}
