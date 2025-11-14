package com.example.bgbg.recommend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.own.dto.response.OwnDetailResponse;
import com.example.bgbg.own.service.OwnService;
import com.example.bgbg.recommend.dto.response.IngredientResponse;
import com.example.bgbg.recommend.service.GptService;
import com.example.bgbg.recommend.service.RecommendedItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
@Tag(name = "Recommend", description = "메뉴 기반 품목 추천 API")
public class RecommendController {

    private final RecommendedItemService recommendedItemService;
    private final OwnService ownService;
    private final GptService gptService;

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @Operation(summary = "메뉴 기반 재료 추천", description = "사용자가 입력한 메뉴를 기반으로 필요한 재료를 추천하고 추천 테이블에 저장")
    @PostMapping
    public ResponseEntity<?> getRecommendation(
            @Parameter(description = "추천받고 싶은 메뉴 이름") @RequestParam String menu) {
        User user = getLoggedInUser();
        List<IngredientResponse> ingredients =
                recommendedItemService.getAndSaveRecommendations(menu, user);
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS, ingredients));
    }

    /*@Operation(summary = "현재 추천받은 품목 조회", description = "현재 저장된 추천 품목 리스트 조회")
    @GetMapping
    public ResponseEntity<?> getCurrentRecommendations() {
        User user = getLoggedInUser();
        List<IngredientResponse> ingredients = recommendedItemService.getCurrentRecommendations(user);
        return ResponseEntity
            .ok()
            .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS, ingredients));
    }*/

    @Operation(
            summary = "추천 품목 전체 장보기 리스트에 추가",
            description = "추천받은 품목 전체를 특정 장보기 리스트에 추가 (개수=1, 메모=null)")
    @PostMapping("/add-to-list")
    public ResponseEntity<?> addAllToShoppingList(
            @Parameter(description = "추가할 장보기 리스트 ID") @RequestParam Long shoppingListId) {
        User user = getLoggedInUser();
        recommendedItemService.addAllToShoppingList(shoppingListId, user);
        return ResponseEntity.ok()
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_ITEM, "추천 품목이 리스트에 추가되었습니다."));
    }

    @Operation(
            summary = "보유 품목 기반 메뉴 추천",
            description = "사용자의 보유 품목을 기반으로 AI가 만들 수 있는 메뉴 3개를 추천")
    @GetMapping("/menu")
    public ResponseEntity<?> getMenuRecommendations() {
        User user = getLoggedInUser();

        // 사용자의 보유 품목 조회
        List<OwnDetailResponse> ownItems = ownService.getAllOwns(user);

        // AI를 통한 메뉴 추천
        List<String> menus = gptService.getMenuRecommendations(ownItems);

        return ResponseEntity.ok()
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_ITEMS, menus));
    }
}
