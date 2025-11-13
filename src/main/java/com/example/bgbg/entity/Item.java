package com.example.bgbg.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.bgbg.shoppinglist.entity.ShoppingList;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private int itemCount;

    // private String itemCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category itemCategory;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = true)
    private ShoppingList shoppingList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "own_item", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean ownItem = false; // 보유 품목 여부(default = false)

    @Column(name = "to_own", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean toOwn = false; // 보유 품목으로 가져갔는지 여부

    @CreatedDate private LocalDateTime createdAt;

    @LastModifiedDate private LocalDateTime updatedAt;

    @Builder
    public Item(
            String itemName,
            int itemCount,
            Category itemCategory,
            String memo,
            ShoppingList shoppingList,
            User user) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCount = itemCount;
        this.memo = memo;
        this.shoppingList = shoppingList;
        this.user = user;
    }

    public void updateItemInfo(String itemName, int itemCount, Category itemCategory) {
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemCategory = itemCategory;
    }

    public void updateItemMemo(String memo) {
        this.memo = memo;
    }

    public void toggleOwnItem() {
        this.ownItem = !this.ownItem;
    }

    public void markToOwn() {
        this.toOwn = true;
    }

    public void unmarkToOwn() {
        this.toOwn = false;
    }

    public void moveToOwnItem() {
        this.toOwn = false;
    }

}
