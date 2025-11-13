package com.example.bgbg.shoppinglist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bgbg.shoppinglist.entity.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {}
