package com.example.bgbg.repository;

import com.example.bgbg.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByShoppingListId(Long shoppingListId); // 리스트id로 품목 조회 메서드
    boolean existsByItemName(String itemName); // 품목 이름 중복 체크 메서드

    // 품목id로 해당 품목이 리스트에 담겨있는지 체크 (shoppingList가 null이 아닌지 확인)
    @Query("SELECT CASE WHEN i.shoppingList IS NOT NULL THEN true ELSE false END FROM Item i WHERE i.id = :itemId")
    boolean isItemInShoppingList(@Param("itemId") Long itemId);
}
