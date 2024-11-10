import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.test.StepVerifier;
import ru.mirea.entity.OrderDto;
import ru.mirea.entity.Orders;
import ru.mirea.entity.Pizza;
import ru.mirea.mapper.OrderMapper;
import ru.mirea.repo.OrderRepository;
import ru.mirea.repo.PizzaRepository;
import ru.mirea.service.OrderService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @Test
    void findAllOrdersByPage() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Orders> orders = new ArrayList<>();
        orders.add(new Orders());
        orders.add(new Orders());
        Page<Orders> page = new PageImpl<>(orders, pageable, orders.size());
        when(orderRepository.findAll(pageable)).thenReturn(page);

        StepVerifier.create(orderService.findAllOrdersByPage(pageable))
                .expectNextCount(2)
                .verifyComplete();

        verify(orderRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAllOrders() {
        List<Orders> orders = new ArrayList<>();
        orders.add(new Orders());
        orders.add(new Orders());
        when(orderRepository.findAll()).thenReturn(orders);

        StepVerifier.create(orderService.findAllOrders())
                .expectNextCount(2)
                .verifyComplete();

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findOrderById() {
        Orders order = new Orders();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        StepVerifier.create(orderService.findOrderById(1L))
                .expectNext(order)
                .verifyComplete();

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void findOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        StepVerifier.create(orderService.findOrderById(1L))
                .expectComplete()
                .verify();

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void createOrder() {
        OrderDto orderDto = new OrderDto();
        Orders order = new Orders();
        when(orderMapper.fromDtoToEntity(orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        StepVerifier.create(orderService.createOrder(orderDto))
                .expectNext(order)
                .verifyComplete();

        verify(orderMapper, times(1)).fromDtoToEntity(orderDto);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void deleteOrderById() {
        doNothing().when(orderRepository).deleteById(1L);

        StepVerifier.create(orderService.deleteOrderById(1L))
                .verifyComplete();

        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void findOrdersByStatus() {
        List<Orders> orders = new ArrayList<>();
        Orders order1 = new Orders();
        order1.setStatus("NEW");
        orders.add(order1);
        Orders order2 = new Orders();
        order2.setStatus("IN_PROGRESS");
        orders.add(order2);
        when(orderRepository.findAll()).thenReturn(orders);

        StepVerifier.create(orderService.findOrdersByStatus("NEW"))
                .expectNext(order1)
                .verifyComplete();

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void addPizzaToOrder() {
        Orders order = new Orders();
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(orderRepository.save(order)).thenReturn(order);

        StepVerifier.create(orderService.addPizzaToOrder(1L, 1L))
                .expectNext(order)
                .verifyComplete();

        verify(orderRepository, times(2)).findById(1L);
        verify(pizzaRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void updateOrderStatus() {
        Orders order = new Orders();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        StepVerifier.create(orderService.updateOrderStatus(1L, "DELIVERED"))
                .expectNext(order)
                .verifyComplete();

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findOrdersByStatusWithBackpressure() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Orders> orders = new ArrayList<>();
        Orders order1 = new Orders();
        order1.setStatus("NEW");
        orders.add(order1);
        when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(orders, pageable, orders.size()));

        StepVerifier.withVirtualTime(() -> orderService.findOrdersByStatusWithBackpressure("NEW", pageable))
                .expectSubscription()
                .thenAwait(Duration.ofMillis(200))
                .expectNext(order1)
                .verifyComplete();

        verify(orderRepository, times(1)).findAll(pageable);
    }
}
