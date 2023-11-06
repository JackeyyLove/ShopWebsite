package com.example.shopapp.service;

import com.example.shopapp.dto.OrderDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getOrderByUserId(Long userId);
    OrderDto getOrderById(Long id);
    OrderDto updateOrder(Long id, OrderDto orderDto);
    String deleteOrder(Long id);
}
