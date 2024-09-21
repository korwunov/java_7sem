package com.spring.rsocketClient.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Good {
    public Long id;
    String name;
    int price;
    String description;
}
