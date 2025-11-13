package com.example.bgbg.own.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class Own {

    @Id
    @Column(name = "own_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "own_name")
    private String ownName;

    @Column(name = "own_count")
    private int ownCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "own_category")
    private Category ownCategory;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Own(User user, String ownName, int ownCount, Category ownCategory) {
        this.user = user;
        this.ownName = ownName;
        this.ownCategory = ownCategory;
        this.ownCount = ownCount;
    }

    public void updateOwn(String ownName, Integer ownCount, Category ownCategory) {
        if (ownName != null && !ownName.isBlank()) {
            this.ownName = ownName;
        }

        if (ownCount != null) {
            this.ownCount = ownCount;
        }

        if (ownCategory != null) {
            this.ownCategory = ownCategory;
        }
    }
}
