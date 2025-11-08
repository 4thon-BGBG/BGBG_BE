package com.example.bgbg.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;  // 아이디

    @Column(nullable = false)
    private String password;  // 비밀번호

    @Transient
    private String confirmPassword; // 비밀번호 확인

    @Column(nullable = false)
    private String nickname;  // 닉네임
}
