package com.example.bgbg.own.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bgbg.common.entity.Category;
import com.example.bgbg.own.entity.Own;
import com.example.bgbg.user.entity.User;

public interface OwnRepository extends JpaRepository<Own, Long> {
    List<Own> findByUser(User user);

    List<Own> findByUserAndOwnCategory(User user, Category ownCategory);

    List<Own> findByUserAndOwnCount(User user, int ownCount);

    Own findByUserAndOwnName(User user, String ownName);
}
