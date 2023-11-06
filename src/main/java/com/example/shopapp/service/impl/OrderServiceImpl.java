package com.example.shopapp.service.impl;

import com.example.shopapp.dto.OrderDto;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderStatus;
import com.example.shopapp.model.User;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Cannot find user with id: " + orderDto.getUserId()));
        Order order = modelMapper.map(orderDto, Order.class);
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        order.setActive(true);
         Order newOrder = orderRepository.save(order);
        OrderDto returnedOrder = modelMapper.map(newOrder, OrderDto.class);
        returnedOrder.setUserId(orderDto.getUserId());
        return returnedOrder;
    }

    @Override
    public List<OrderDto> getOrderByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Cannot find user with id: " + userId));
        List<Order> orderList = orderRepository.findByUserId(userId);
        List<OrderDto> orderDtoList = orderList.stream().map(order -> modelMapper.map(order, OrderDto.class)).toList();
        return orderDtoList;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: " + id)) ;
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: " + id));
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Cannot find user with id: " + orderDto.getUserId()));
        order.setUser(user);
        order.setFullName(orderDto.getFullName());
        order.setEmail(orderDto.getEmail());
        order.setAddress(orderDto.getAddress());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        order.setNote(orderDto.getNote());
        order.setTotalMoney(orderDto.getTotalMoney());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        Order updatedOrder = orderRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDto.class);
    }

    @Override
    public String deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: " + id));
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
        return "Deleted successfully!";
    }
}
