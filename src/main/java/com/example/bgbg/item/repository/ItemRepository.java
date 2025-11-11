package com.example.bgbg.item.repository;

import com.example.bgbg.entity.User;
import com.example.bgbg.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUser(User user);
    List<Item> findByUserAndItemCategory(User user, String itemCategory);
    List<Item> findByUserAndItemCount(User user, int itemCount);
}
