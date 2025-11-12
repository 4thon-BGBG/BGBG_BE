package com.example.bgbg.own.repository;

import com.example.bgbg.entity.Category;
import com.example.bgbg.entity.User;
import com.example.bgbg.own.entity.Own;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnRepository extends JpaRepository<Own, Long> {
    List<Own> findByUser(User user);
    List<Own> findByUserAndOwnCategory(User user, Category ownCategory);
    List<Own> findByUserAndOwnCount(User user, int ownCount);
}
