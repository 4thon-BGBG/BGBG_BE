package com.example.bgbg.own.mapper;

import com.example.bgbg.entity.User;
import com.example.bgbg.own.dto.request.OwnCreatedRequest;
import com.example.bgbg.own.dto.response.OwnDetailResponse;
import com.example.bgbg.own.entity.Own;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OwnMapper {
    public static Own toEntity(User user, OwnCreatedRequest request) {
        return Own.builder()
                .user(user)
                .ownName(request.ownName())
                .ownCategory(request.ownCategory())
                .ownCount(request.ownCount())
                .build();
    }

    public static OwnDetailResponse toDetailResponse(Own own) {
        if (own == null) {
            throw new IllegalArgumentException("품목이 없습니다.");
        }

        return OwnDetailResponse.builder()
                .userId(own.getUser().getId())
                .ownId(own.getId())
                .ownName(own.getOwnName())
                .ownCount(own.getOwnCount())
                .ownCategory(own.getOwnCategory().name())
                .updateAt(own.getUpdatedAt())
                .build();
    }
}
