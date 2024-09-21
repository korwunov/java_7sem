package com.spring.rsocket.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    String name;
    int price;
    String description;
}
