package com.example.bgbg.dto.response;

import com.example.bgbg.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ListResponse DTO", description = "새로운 리스트 생성 응답 반환")
public class ItemGetResponse {

  @Schema(description = "리스트 이름")
  private String listName;

  @Schema(description = "품목 이름")
  private String itemName;

  @Schema(description = "품목 개수")
  private String itemCount;

  @Schema(description = "품목 카테고리")
  private Category category;

  @Schema(description = "품목 메모")
  private String memo;

}
