package ru.mirea.mapper;

import org.mapstruct.Mapper;

import ru.mirea.entity.OrderDto;
import ru.mirea.entity.Orders;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto fromEntityToDto(Orders order);

    Orders fromDtoToEntity(OrderDto orderDTO);
}
