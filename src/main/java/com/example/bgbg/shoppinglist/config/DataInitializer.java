package com.example.bgbg.shoppinglist.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.bgbg.shoppinglist.entity.ShoppingList;
import com.example.bgbg.shoppinglist.repository.ShoppingListRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final ShoppingListRepository shoppingListRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (!shoppingListRepository.existsById(1L)) {
            ShoppingList defaultList = ShoppingList.builder().listName("장보기 리스트").build();
            shoppingListRepository.save(defaultList);
        }
    }
}
