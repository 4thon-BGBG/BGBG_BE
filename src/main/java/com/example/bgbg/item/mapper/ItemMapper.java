package com.example.bgbg.item.mapper;

import com.example.bgbg.entity.User;
import com.example.bgbg.item.dto.request.ItemCreatedRequest;
import com.example.bgbg.item.dto.response.ItemDetailResponse;
import com.example.bgbg.item.entity.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    public static Item toEntity(User user , ItemCreatedRequest request) {
        return Item.builder()
                .user(user)
                .itemName(request.itemName())
                .itemCategory(request.itemCategory())
                .itemCount(request.itemCount())
                .build();
    }

    public static ItemDetailResponse toDetailResponse(Item item) {
        if(item == null) {
            throw new IllegalArgumentException("품목이 없습니다.");
        }

        return ItemDetailResponse.builder()
                .userId(item.getUser().getId())
                .itemId(item.getId())
                .itemName(item.getItemName())
                .itemCount(item.getItemCount())
                .itemCategory(item.getItemCategory())
                .build();

    }
}
