package com.example.bgbg.controller;

import com.example.bgbg.dto.request.ItemCreatedRequest;
import com.example.bgbg.dto.response.ItemCreatedResponse;
import com.example.bgbg.service.ItemService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/item")
    public ItemCreatedResponse createItem(@RequestBody ItemCreatedRequest request) {

        return itemService.saveItem(request);
    }
}
