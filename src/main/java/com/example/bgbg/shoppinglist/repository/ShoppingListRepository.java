package com.example.bgbg.shoppinglist.repository;

import com.example.bgbg.shoppinglist.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

}
