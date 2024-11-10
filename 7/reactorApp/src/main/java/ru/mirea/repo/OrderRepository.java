package ru.mirea.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
