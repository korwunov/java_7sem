package ru.mirea.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mirea.entity.OrderDto;
import ru.mirea.entity.Orders;
import ru.mirea.service.OrderService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Flux<Orders> getAllOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        return orderService.findAllOrdersByPage(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Mono<Orders> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Orders> createOrder(@RequestBody OrderDto order) {
        return orderService.createOrder(order);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteOrder(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

    @GetMapping("/status")
    public Flux<Orders> getOrdersByStatus(
            @RequestParam("status") String status
    ) {
        return orderService.findOrdersByStatus(status);
    }

    @PutMapping("/{orderId}/pizza/{pizzaId}")
    public Mono<Orders> addPizzaToOrder(
            @PathVariable Long orderId,
            @PathVariable Long pizzaId
    ) {
        return orderService.addPizzaToOrder(orderId, pizzaId);
    }

    @PatchMapping("/{orderId}/status")
    public Mono<Orders> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam("status") String status
    ) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/cost")
    public Flux<Orders> findAllOrdersWithTotalCost() {
        return orderService.findAllOrdersWithTotalCost();
    }
}