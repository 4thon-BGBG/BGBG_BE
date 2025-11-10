package com.example.bgbg.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    VEGETABLES_FRUITS("채소/과일류"),
    MEAT("육류"),
    SEAFOOD("수산물"),
    EGGS_DAIRY("달걀/유제품류"),
    GRAINS_NUTS("곡류/견과류"),
    SEASONINGS("조미/양념류"),
    FROZEN_FOOD("냉동식품"),
    PROCESSED_FOOD("가공식품"),
    BEVERAGES_ALCOHOL("음료/주류"),
    ETC("기타");

    private final String description;
}
