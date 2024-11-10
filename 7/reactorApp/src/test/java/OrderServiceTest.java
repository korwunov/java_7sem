/*
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.test.StepVerifier;
import ru.mirea.entity.Orders;
//import ru.mirea.entity.OrderItem;
import ru.mirea.entity.Pizza;
//import ru.mirea.repo.OrderItemRepository;
import ru.mirea.repo.OrderRepository;
import ru.mirea.repo.PizzaRepository;
import ru.mirea.service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    //@Mock
    //private OrderItemRepository orderItemRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void findAllOrders() {
        List<Orders> orders = Arrays.asList(
                Orders.builder().id(1L).customerName("John Doe").status("NEW").build(),
                Orders.builder().id(2L).customerName("Jane Doe").status("IN_PROGRESS").build()
        );
        when(orderRepository.findAll()).thenReturn(orders);

        StepVerifier.create(orderService.findAllOrders())
                .expectNext(orders.get(0))
                .expectNext(orders.get(1))
                .verifyComplete();
    }

    */
/*@Test
    void findOrderById() {
        Orders order = Orders.builder().id(1L).customerName("John Doe").status("NEW").build();
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        StepVerifier.create(orderService.findOrderById(1L))
                .expectNext(order)
                .verifyComplete();
    }*//*


    */
/*@Test
    void createOrder() {
        Orders order = Orders.builder().id(1L).customerName("John Doe").status("NEW").build();
        when(orderRepository.save(order)).thenReturn(order);

        StepVerifier.create(orderService.createOrder(order))
                .expectNext(order)
                .verifyComplete();
    }*//*


    @Test
    void deleteOrderById() {
        doNothing().when(orderRepository).deleteById(1L);

        StepVerifier.create(orderService.deleteOrderById(1L))
                .verifyComplete();
    }

    @Test
    void findOrdersByStatus() {
        List<Orders> orders = Arrays.asList(
                Orders.builder().id(1L).customerName("John Doe").status("NEW").build(),
                Orders.builder().id(2L).customerName("Jane Doe").status("IN_PROGRESS").build()
        );
        when(orderRepository.findAll()).thenReturn(orders);

        StepVerifier.create(orderService.findOrdersByStatus("NEW", PageRequest.of(0, 10)))
                .expectNext(orders.get(0))
                .verifyComplete();
    }

    */
/*@Test
    void addPizzaToOrder() {
        Order order = Order.builder().id(1L).customerName("John Doe").status("NEW").build();
        Pizza pizza = Pizza.builder().id(1L).name("Pepperoni").price(10.0).build();
        OrderItem orderItem = OrderItem.builder().id(1L).order(order).pizza(pizza).quantity(2).build();
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(pizzaRepository.findById(1L)).thenReturn(java.util.Optional.of(pizza));
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        StepVerifier.create(orderService.addPizzaToOrder(1L, 1L, 2))
                .expectNext(order)
                .verifyComplete();
    }*//*


    */
/*@Test
    void addPizzaToOrder_pizzaNotFound() {
        Order order = Order.builder().id(1L).customerName("John Doe").status("NEW").build();
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(pizzaRepository.findById(1L)).thenThrow(Exception.class);

        StepVerifier.create(orderService.addPizzaToOrder(1L, 1L, 2))
                .expectError(Exception.class)
                .verify();
    }
*//*

    @Test
    void updateOrderStatus() {
        Orders order = Orders.builder().id(1L).customerName("John Doe").status("NEW").build();
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        StepVerifier.create(orderService.updateOrderStatus(1L, "IN_PROGRESS"))
                .expectNext(order)
                .verifyComplete();
    }
}
*/
