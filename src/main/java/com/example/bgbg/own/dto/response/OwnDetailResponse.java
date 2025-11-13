package com.example.bgbg.own.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record OwnDetailResponse(
        Long userId,
        Long ownId,
        String ownName,
        int ownCount,
        String ownCategory,
        LocalDateTime updateAt) {}
