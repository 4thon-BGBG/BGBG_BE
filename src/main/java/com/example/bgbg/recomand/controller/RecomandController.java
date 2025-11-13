package com.example.bgbg.recomand.controller;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.recomand.dto.response.IngredientResponse;
import com.example.bgbg.recomand.service.GptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recomand")
@Tag(name = "Recomand", description = "메뉴 기반 품목 추천 API")
public class RecomandController {

    private final GptService gptService;

    @Operation(summary = "메뉴 기반 재료 추천", description = "사용자가 입력한 메뉴를 기반으로 필요한 재료를 추천")
    @GetMapping
    public ResponseEntity<?> getRecommendation(@Parameter(description = "추천받고 싶은 메뉴 이름")
                                                @RequestParam String menu) {
        List<IngredientResponse> ingredients = gptService.getIngredients(menu);
        return ResponseEntity
            .ok()
            .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS, ingredients));
    }

}
