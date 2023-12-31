package com.example.shopapp.dto;

import com.example.shopapp.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDto {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 0" )
    private Long productId;
    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image's name")
    private String imageUrl;
}
