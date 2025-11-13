package com.example.bgbg.shoppinglist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "CreateListRequest DTO", description = "새로운 리스트를 추가하기 위한 요청 DTO")
public class CreateListRequest {

    @Schema(description = "리스트 이름", example = "리스트1")
    private String listName;
}
