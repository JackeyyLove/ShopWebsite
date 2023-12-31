package com.example.shopapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
}
