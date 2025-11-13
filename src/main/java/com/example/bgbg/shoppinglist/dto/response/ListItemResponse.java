package com.example.bgbg.shoppinglist.dto.response;

import com.example.bgbg.dto.response.ItemGetResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(title = "ListItemResponse DTO", description = "장보기 리스트와 품목 조회 응답 반환")
public class ListItemResponse {

  @Schema(description = "리스트 ID")
  private Long listId;

  @Schema(description = "리스트 이름")
  private String listName;

  @Schema(description = "리스트에 담긴 품목들")
  private List<ItemGetResponse> items;
}
