package com.example.bgbg.own.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.bgbg.code.ResponseCode;
import com.example.bgbg.dto.response.ResponseDTO;
import com.example.bgbg.entity.User;
import com.example.bgbg.own.dto.request.OwnCreatedRequest;
import com.example.bgbg.own.dto.request.OwnUpdateRequest;
import com.example.bgbg.own.dto.response.OwnCreatedResponse;
import com.example.bgbg.own.dto.response.OwnDetailResponse;
import com.example.bgbg.own.service.OwnService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owns")
@Tag(name = "Own", description = "보유 품목 관련 API")
public class OwnController {
    private final OwnService ownService;

    @Operation(summary = "보유 품목 수동 추가", description = "새로운 보유 품목 수동 추가")
    @PostMapping
    public ResponseEntity<?> createOwn(
            @RequestBody OwnCreatedRequest request, @AuthenticationPrincipal User loginUser) {
        OwnCreatedResponse response = ownService.saveOwn(loginUser, request);
        return ResponseEntity.status(ResponseCode.SUCCESS_CREATE_OWN.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_CREATE_OWN, response));
    }

    @Operation(summary = "소진된 품목 조회", description = "보유 품목에서 소진된 품목 조회")
    @GetMapping("/depleted")
    public ResponseEntity<?> getDepletedOwns(@AuthenticationPrincipal User loginUser) {
        List<OwnDetailResponse> owns = ownService.getDepletedOwns(loginUser);
        return ResponseEntity.status(ResponseCode.SUCCESS_GET_DEPLETED_OWNS.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_DEPLETED_OWNS, owns));
    }

    @Operation(summary = "보유 품목 단건 조회", description = "ownId를 통한 보유 품목 단건 조회")
    @GetMapping("/{ownId}")
    public ResponseEntity<?> getOwnById(
            @PathVariable Long ownId, @AuthenticationPrincipal User loginUser) {
        OwnDetailResponse own = ownService.getOwnById(loginUser, ownId);
        return ResponseEntity.status(ResponseCode.SUCCESS_GET_OWN.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_GET_OWN, own));
    }

    @Operation(
            summary = "전체 또는 카테고리 별 유저의 보유 품목 조회",
            description = "category 미입력 시 전체 조회 가능한 카테고리 별 보유 품목 조회 api")
    @GetMapping
    public ResponseEntity<?> getAllOwnsByCategory(
            @RequestParam(required = false) String category,
            @AuthenticationPrincipal User loginUser) {
        List<OwnDetailResponse> owns;
        ResponseCode responseCode;

        if (category != null && !category.isEmpty()) {
            owns = ownService.getOwnsByCategory(loginUser, category);
            responseCode = ResponseCode.SUCCESS_GET_CATEGORY_OWN;
        } else {
            owns = ownService.getAllOwns(loginUser);
            responseCode = ResponseCode.SUCCESS_GET_ALL_OWNS;
        }

        return ResponseEntity.status(responseCode.getStatus().value())
                .body(new ResponseDTO<>(responseCode, owns));
    }

    @Operation(summary = "보유 품목 수정", description = "보유 품목 정보 수정")
    @PatchMapping("/{ownId}")
    public ResponseEntity<?> updateOwn(
            @PathVariable Long ownId,
            @RequestBody OwnUpdateRequest request,
            @AuthenticationPrincipal User loginUser) {
        ownService.updateOwn(loginUser, ownId, request);

        return ResponseEntity.status(ResponseCode.SUCCESS_UPDATE_OWN.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_UPDATE_OWN, null));
    }

    @Operation(summary = "보유 품목 삭제", description = "보유 품목 삭제")
    @DeleteMapping("/{ownId}")
    public ResponseEntity<?> deleteOwn(
            @PathVariable Long ownId, @AuthenticationPrincipal User loginUser) {
        ownService.deleteOwn(loginUser, ownId);

        return ResponseEntity.status(ResponseCode.SUCCESS_DELETE_OWN.getStatus().value())
                .body(new ResponseDTO<>(ResponseCode.SUCCESS_DELETE_OWN, null));
    }
}
