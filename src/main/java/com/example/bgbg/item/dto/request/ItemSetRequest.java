package com.example.bgbg.dto.request;

import com.example.bgbg.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ItemSetRequest DTO", description = "특정 리스트의 품목 정보 수정 요청 DTO")
public class ItemSetRequest {

    @Schema(description = "리스트 id")
    private Long shoppingListId;

    @Schema(description = "품목 id")
    private Long itemId;

    @Schema(description = "품목 이름")
    private String itemName;

    @Schema(description = "품목 개수")
    private int itemCount;

    @Schema(description = "품목 카테고리")
    private Category category;

}
