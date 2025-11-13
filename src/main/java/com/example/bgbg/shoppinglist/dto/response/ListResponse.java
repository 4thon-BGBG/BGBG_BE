package com.example.bgbg.shoppinglist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ListResponse DTO", description = "새로운 리스트 생성 응답 반환")
public class ListResponse {

    @Schema(description = "새로 생성한 리스트 고유 id")
    private Long id;

    @Schema(description = "새로 생성한 리스트 이름")
    private String listName;
}
