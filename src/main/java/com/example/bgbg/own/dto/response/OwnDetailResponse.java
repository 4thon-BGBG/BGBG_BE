package com.example.bgbg.own.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OwnDetailResponse(
        Long userId,
        Long ownId,
        String ownName,
        int ownCount,
        String ownCategory,
        LocalDateTime updateAt
) {
}
