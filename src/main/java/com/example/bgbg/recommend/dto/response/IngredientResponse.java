package com.example.bgbg.recommend.dto.response;

import com.example.bgbg.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "IngredientResponse DTO", description = "재료 정보 응답 DTO")
public class IngredientResponse {

    @Schema(description = "재료 이름")
    private String name;

    @Schema(description = "재료 카테고리")
    private Category category;
}