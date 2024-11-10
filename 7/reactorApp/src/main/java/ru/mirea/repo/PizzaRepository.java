package ru.mirea.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.entity.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}