package com.example.shopapp.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_images")
@Data // toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name="image_url", length = 300, nullable = false)
    private String imageUrl;
}
