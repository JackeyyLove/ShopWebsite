package com.example.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDto {
    private Long id;
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order's ID must be > 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 0")
    private Long productId;

    @Min(value = 0, message =  "price must be >= 0")
    private Long price;

    @JsonProperty("number_of_products")
    @Min(value = 1, message = "number of products must be greater than 1")
    private int numberOfProducts;

    @Min(value = 0, message =  "price must be >= 0")
    @JsonProperty("total_money")
    private int totalMoney;
    private String color;
}
