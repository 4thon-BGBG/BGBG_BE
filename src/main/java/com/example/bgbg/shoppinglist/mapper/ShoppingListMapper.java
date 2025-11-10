package com.example.bgbg.shoppinglist.mapper;

import com.example.bgbg.shoppinglist.dto.response.ListResponse;
import com.example.bgbg.shoppinglist.entity.ShoppingList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ShoppingListMapper {
  // entity -> response dto
  public ListResponse toListResponse(ShoppingList shoppingList) {
    return  ListResponse.builder()
        .id(shoppingList.getId())
        .listName(shoppingList.getListName())
        .build();
  }
}
