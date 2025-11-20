package com.example.bgbg.user.dto;

import com.example.bgbg.user.entity.User;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String token;
    private Long id;
    private String username;
    private String nickname;

    public UserResponseDTO(User user) {
        if (user != null) {
            this.username = user.getUsername();
        }
    }
}
