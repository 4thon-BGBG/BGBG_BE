package com.example.bgbg.recommend.repository;

import com.example.bgbg.entity.User;
import com.example.bgbg.recommend.entity.RecommendedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendedItemRepository extends JpaRepository<RecommendedItem, Long> {

    // 특정 사용자의 추천 품목 전체 조회
    List<RecommendedItem> findByUser(User user);

    // 특정 사용자의 추천 품목 전체 삭제
    void deleteByUser(User user);
}