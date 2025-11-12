package com.example.bgbg.repository;

import com.example.bgbg.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByShoppingListId(Long shoppingListId); // 리스트id로 품목 조회 메서드
}
