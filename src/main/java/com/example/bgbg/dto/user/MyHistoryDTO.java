package com.example.bgbg.dto.user;

import lombok.*;

import java.time.LocalDate;

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
