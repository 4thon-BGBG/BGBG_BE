package com.example.bgbg.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ItemMemoRequest DTO", description = "특정 리스트의 품목의 메모 작성(수정) 요청 DTO")
public class ItemMemoRequest {

    @Schema(description = "리스트 id")
    private Long shoppingListId;

    @Schema(description = "품목 id")
    private Long itemId;

    @Schema(description = "품목 메모 내용")
    private String memo;


}
