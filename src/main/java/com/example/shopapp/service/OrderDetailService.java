package com.example.shopapp.service;

import com.example.shopapp.dto.OrderDetailDto;
import com.example.shopapp.model.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDto orderDetailDto);
    OrderDetail getOrderDetail(Long id);
    OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto);
    List<OrderDetail> getAllOrderDetail();
    String deleteOrderDetail(Long id);
}
