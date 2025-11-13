package com.example.bgbg.recommend.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.bgbg.common.BaseTimeEntity;
import com.example.bgbg.entity.Category;
import com.example.bgbg.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RecommendedItem extends BaseTimeEntity {

    @Id
    @Column(name = "recommended_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category itemCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public RecommendedItem(String itemName, Category itemCategory, User user) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.user = user;
    }
}
