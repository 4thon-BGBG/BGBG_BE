package com.example.bgbg.own.dto.request;

import com.example.bgbg.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OwnCreatedRequest(
        @NotBlank(message = "보유 품목 이름은 필수입니다.")
        String ownName,

        @Min(value = 0, message = "보유 개수는 0개 이상이어야 합니다.")
        int ownCount,

        @NotNull(message = "카테고리는 필수입니다.")
        Category ownCategory
) {}
