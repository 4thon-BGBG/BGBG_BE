package com.example.bgbg.own.dto.request;

import com.example.bgbg.entity.Category;

public record OwnUpdateRequest(
        String ownName,
        Integer ownCount,
        Category ownCategory
) {
}
