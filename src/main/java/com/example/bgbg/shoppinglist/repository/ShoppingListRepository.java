package com.example.bgbg.shoppinglist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.user.entity.User;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    List<ShoppingList> findByUser(User user);
}
