package com.example.bgbg.shoppinglist.entity;

import com.example.bgbg.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  public void updateName(String listName) {
    this.listName = listName;
  }

}
