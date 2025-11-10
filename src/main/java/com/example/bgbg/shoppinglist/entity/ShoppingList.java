package com.example.bgbg.shoppinglist.entity;

import com.example.bgbg.common.BaseTimeEntity;
import com.example.bgbg.entity.Item;
import com.example.bgbg.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shoppingList")
public class ShoppingList extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "listName")
  private String listName; // 리스트 이름

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Item> items = new ArrayList<>();

  public void updateName(String listName) {
    this.listName = listName;
  }

}
