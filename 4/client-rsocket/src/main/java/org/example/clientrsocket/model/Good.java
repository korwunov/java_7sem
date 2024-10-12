package org.example.clientrsocket.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class Good {
    public Long id;
    public String name;
    public int price;
    public String description;
}
