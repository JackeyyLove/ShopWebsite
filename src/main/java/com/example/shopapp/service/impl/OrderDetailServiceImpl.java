package com.example.shopapp.service.impl;

import com.example.shopapp.dto.OrderDetailDto;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetail;
import com.example.shopapp.model.Product;
import com.example.shopapp.repository.OrderDetailRepository;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.ProductRepository;
import com.example.shopapp.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    @Override
    public OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) {
        Order order = orderRepository.findById(orderDetailDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: "  + orderDetailDto.getOrderId()));
        Product product = productRepository.findById(orderDetailDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: " + orderDetailDto.getProductId()));
        OrderDetail newOrderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailDto.getPrice())
                .totalMoney(orderDetailDto.getTotalMoney())
                .color(orderDetailDto.getColor())
                .numberOfProducts(orderDetailDto.getNumberOfProducts())
                .build();
        return orderDetailRepository.save(newOrderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order detail with id: " + id));
        return orderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order detail with id: " + id));
        Order order = orderRepository.findById(orderDetailDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: "  + orderDetailDto.getOrderId()));
        Product product = productRepository.findById(orderDetailDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Cannot find order with id: " + orderDetailDto.getProductId()));
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        orderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProducts());
        orderDetail.setColor(orderDetailDto.getColor());
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> getAllOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        return orderDetails;
    }

    @Override
    public String deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find order detail with id: " + id));
        orderDetailRepository.delete(orderDetail);
        return "deleted successfully";
    }
}
