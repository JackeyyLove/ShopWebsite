package com.example.shopapp.controller;


import com.example.shopapp.dto.OrderDetailDto;
import com.example.shopapp.service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDto orderDetailDto,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errorMessage = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

            return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(orderDetailService.getOrderDetail(id) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDto newOrderDetailData
    ) {
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, newOrderDetailData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@Valid @PathVariable("id") Long orderDetailId) {
        return ResponseEntity.ok(orderDetailService.deleteOrderDetail(orderDetailId));
    }
}
