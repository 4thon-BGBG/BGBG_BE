package com.example.bgbg.shoppinglist.mapper;

import org.springframework.stereotype.Component;

import com.example.bgbg.shoppinglist.dto.response.ListResponse;
import com.example.bgbg.shoppinglist.entity.ShoppingList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ShoppingListMapper {
    // entity -> response dto
    public ListResponse toListResponse(ShoppingList shoppingList) {
        return ListResponse.builder()
                .id(shoppingList.getId())
                .listName(shoppingList.getListName())
                .build();
    }
}
