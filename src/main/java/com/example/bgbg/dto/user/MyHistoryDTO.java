package com.example.bgbg.dto.user;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyHistoryDTO {
    private Long ownId;
    private String name;
    private int count;
    private LocalDate purchaseDate;
}
