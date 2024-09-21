package com.spring.rsocket.repositories;

import com.spring.rsocket.entities.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Good, Long> {
}
