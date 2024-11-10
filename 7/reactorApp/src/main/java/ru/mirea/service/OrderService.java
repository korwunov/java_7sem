package ru.mirea.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import ru.mirea.entity.OrderDto;
import ru.mirea.entity.Orders;

import ru.mirea.entity.Pizza;
import ru.mirea.mapper.OrderMapper;
import ru.mirea.repo.OrderRepository;
import ru.mirea.repo.PizzaRepository;

import java.util.Set;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final PizzaRepository pizzaRepository;

    public OrderService(OrderMapper orderMapper, OrderRepository orderRepository, PizzaRepository pizzaRepository) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.pizzaRepository = pizzaRepository;
    }

    public Flux<Orders> findAllOrdersByPage(Pageable pageable) {
        return Flux.fromIterable(orderRepository.findAll(pageable));
    }

    public Flux<Orders> findAllOrders() {
        return Flux.fromIterable(orderRepository.findAll());
    }

    public Mono<Orders> findOrderById(Long id) {
        return Mono.justOrEmpty(orderRepository.findById(id));
    }

    public Mono<Orders> createOrder(OrderDto dto) {
        return Mono.just(orderRepository.save(orderMapper.fromDtoToEntity(dto)));
    }

    public Mono<Void> deleteOrderById(Long id) {
        return Mono.fromRunnable(() -> orderRepository.deleteById(id));
    }

    public Flux<Orders> findOrdersByStatus(String status) {
        return Flux.fromIterable(orderRepository.findAll())
                .filter(order -> order.getStatus().equalsIgnoreCase(status));
    }

    public Mono<Orders> addPizzaToOrder(Long orderId, Long pizzaId) {
        return findOrderById(orderId)
                .flatMap(order -> {
                    Pizza pizza = null;
                    try {
                        pizza = pizzaRepository.findById(pizzaId)
                                .orElseThrow(() -> new Exception("Не найдена пицца с id: " + pizzaId));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Set<Pizza> pizzaz = order.getPizzas();
                    pizzaz.add(pizza);
                    orderRepository.save(order);
                    return Mono.just(order);
                })
                .flatMap(orderItem -> findOrderById(orderId));
    }

    public Mono<Orders> updateOrderStatus(Long orderId, String status) {
        return findOrderById(orderId)
                .flatMap(order -> {
                    order.setStatus(status);
                    return Mono.just(orderRepository.save(order));
                });
    }

    // Пример преобразования потока:
    public Flux<Orders> findAllOrdersWithTotalCost() {
        return findAllOrders()
                .flatMap(order -> {
                    return calculateTotalCost(order)
                            .map(totalCost -> {
                                order.setTotalCost(totalCost);
                                return orderRepository.save(order);
                            });
                });
    }

    private Mono<Double> calculateTotalCost(Orders order) {
        return Flux.fromIterable(order.getPizzas())
                .map(p -> p.getPrice())
                .reduce(0.0, Double::sum)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(throwable -> {
                    System.err.println("Ошибка при расчете стоимости заказа: " + throwable.getMessage());
                });
    }

    // Пример backpressure:
    public Flux<Orders> findOrdersByStatusWithBackpressure(String status, Pageable pageable) {
        return Flux.fromIterable(orderRepository.findAll(pageable))
                .filter(order -> order.getStatus().equalsIgnoreCase(status))
                .onBackpressureBuffer(10)
                .doOnNext(order -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }
}
